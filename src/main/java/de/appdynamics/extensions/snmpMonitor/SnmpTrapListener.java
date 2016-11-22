package de.appdynamics.extensions.snmpMonitor;


import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.apache.log4j.Logger;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpTrapListener implements CommandResponder{

    private final SnmpTrapMonitorConfig _cfg;
    private Set<SNMPMetricConsumer> _consumers = new HashSet<SNMPMetricConsumer>();
    private static Logger logger = Logger.getLogger(SnmpTrapListener.class);
    public boolean isRunning() {
        return _running;
    }
    private boolean _running;

    private ThreadPool tPool;
    private Snmp snmpCore;
    private MultiThreadedMessageDispatcher dispatcher;
    private Address listenerAddress;


    public static final OID SNMP_TRAP_OID = new OID("1.3.6.1.6.3.1.1.4.1.0");
    /**
     * Constructor
     * @param cfg Configuration from local config.yml file to start the listener
     */
    public SnmpTrapListener(SnmpTrapMonitorConfig cfg) {
        _cfg = cfg;

    }

    /** starts the SNMP Trap receiver and will start listening for Events coming in
     *
     */
    public void start() {
        _running =true;
        try {
            logger.debug("Started listening on port "+ _cfg.getEndpointConfig().getListenerPort() + " !!!!");
            //Start listening
            initialize();
            snmpCore.addCommandResponder(this);
        }
        catch (Exception e){
            e.printStackTrace();
            _running =false;
        }



    }

    /**
     * Performs the initial configuration for the SNMP trap listener
     * @throws IOException
     * @throws UnknownHostException
     */
    public void initialize() throws IOException, UnknownHostException {
        tPool = ThreadPool.create("SNMPTRAP" , 5);
        dispatcher = new MultiThreadedMessageDispatcher(tPool, new MessageDispatcherImpl());
        listenerAddress = GenericAddress.parse(System.getProperty
                            ("snmp4j.listenAddress", "udp:"
                                    + _cfg.getEndpointConfig().getListenerAddress()
                                    + "/"
                                    + _cfg.getEndpointConfig().getListenerPort()));
        TransportMapping tMapping = new DefaultUdpTransportMapping((UdpAddress)listenerAddress);
        snmpCore = new Snmp(dispatcher, tMapping);
        snmpCore.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmpCore.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmpCore.listen();
    }

    @Override
    public void processPdu(CommandResponderEvent commandResponderEvent) {
        StringBuffer message = new StringBuffer();
        int type = commandResponderEvent.getPDU().getType();

        String trap = "";
        switch (type) {
            case PDU.V1TRAP:
                PDUv1 v1pdu = (PDUv1) commandResponderEvent.getPDU();

                trap = v1pdu.getEnterprise().toDottedString();
                break;
            case PDU.TRAP:
                 trap = commandResponderEvent.getPDU().getVariable(SNMP_TRAP_OID).toString();

                break;
        }



        message.append("\nTRAP Received !!!!"
                + "\n" + "TRAP OID : " +  trap +"\n");

        for (VariableBinding v : commandResponderEvent.getPDU().getVariableBindings()) {
            message.append(v.getOid().toString())
                    .append("  : ")
                    .append(v.getVariable().toString())
                    .append("\n");
        }

        logger.debug(message);

        Vector<? extends VariableBinding> bindings = commandResponderEvent.getPDU().getVariableBindings();
        PDU pdu = commandResponderEvent.getPDU();

        Iterator<TrapConfig> trapConfigs = _cfg.getTraps().values().iterator();
        while (trapConfigs.hasNext()) {
            TrapConfig cfg = trapConfigs.next();

            if (trap.equals(cfg.getOid())) {


                for (SNMPMetricConsumer metricConsumer : _consumers)
                {
                    metricConsumer.reportTrap(cfg,pdu);

                }

            }
        }




//        for (VariableBinding vb : varBindings)
//        {
//            //Proceed only if the incoming trap has been configured locally
//            if (_cfg.getTraps().containsKey(vb.getOid().toString()))
//            {
//                message.append("\nTRAP Received !!!!"
//                        + "\n" + "TRAP Name : " + _cfg.getTraps().get(vb.getOid().toString()).get_name()
//                        + "\n" + "TRAP OID : " + vb.getOid().toString()
//                        + "\n" + "TRAP Data : " + vb.getVariable());
//
//                logger.debug(message);
//
//                for (SNMPMetricConsumer metricConsumer : _consumers)
//                {
//                    metricConsumer.reportTrap(_cfg.getTraps().get(vb.getOid().toString()).get_name());
//
//                }
//            }
//        }
    }

    public void registerSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        _consumers.add(snmpMetricConsumer);

    }

    public void unRegisterSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        if (_consumers.contains(snmpMetricConsumer)) _consumers.remove(snmpMetricConsumer);

    }
}
