package de.appdynamics.extensions.snmpMonitor;


import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import org.apache.log4j.Logger;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

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
        }
        finally {
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
        logger.debug("TRAP Received !!!!");
        Vector <VariableBinding> varBindings = commandResponderEvent.getPDU().getVariableBindings();

        // TODO: only do this if the trap has been configured in cfg.getTraps()...
        // TODO: specify a trap name in cfg that's used

        for (VariableBinding vb : varBindings)
        {
            message.append(vb.getOid().toString()
                            + " : "
                            + vb.getVariable() + "\n");
        }
        logger.debug(message);

    }

    public void registerSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        _consumers.add(snmpMetricConsumer);

    }

    public void unRegisterSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        if (_consumers.contains(snmpMetricConsumer)) _consumers.remove(snmpMetricConsumer);

    }
}
