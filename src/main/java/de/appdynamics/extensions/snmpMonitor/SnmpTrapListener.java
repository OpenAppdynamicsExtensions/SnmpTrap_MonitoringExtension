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
import java.net.ServerSocket;
import java.net.Socket;
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
    public void start() throws AgentException {
        _running =true;
        try {
            //Listen to the port only if it is not already used
            if (isPortAvailable(_cfg.getEndpointConfig().getListenerAddress(), _cfg.getEndpointConfig().getListenerPort()))
            {
                //Start listening
                initialize();
                logger.debug("Started listening on port " + _cfg.getEndpointConfig().getListenerPort() + " !!!!");
                snmpCore.addCommandResponder(this);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            _running =false;
        }
    }

    /**
     * Checks if a port is already opened and used
     * @param UDPAddress
     * @param port
     * @return
     * @throws IOException
     */
    private boolean isPortAvailable(String UDPAddress, int port) throws AgentException {
        ServerSocket soc=null;
        try {
            soc = new ServerSocket(port);
            soc.close();
            soc=null;
            return  true;
        }
        catch (IOException e)
        {
            return false;
        }
        /*finally {
            if (soc!=null)
            {
                try {
                    soc.close();
                } catch (IOException e) {
                    throw new AgentException("Error closing socket", e);
                }
            }
        }*/
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
        Vector <VariableBinding> varBindings = commandResponderEvent.getPDU().getVariableBindings();
        for (VariableBinding vb : varBindings)
        {
            //Proceed only if the incoming trap has been configured locally
            if (_cfg.getTraps().containsKey(vb.getOid().toString()))
            {
                message.append("\nTRAP Received !!!!"
                        + "\n" + "TRAP Name : " + _cfg.getTraps().get(vb.getOid().toString()).get_name()
                        + "\n" + "TRAP OID : " + vb.getOid().toString()
                        + "\n" + "TRAP Data : " + vb.getVariable());

                logger.debug(message);

                for (SNMPMetricConsumer metricConsumer : _consumers)
                {
                    metricConsumer.reportTrap(_cfg.getTraps().get(vb.getOid().toString()).get_name());

                }
            }
        }
    }

    public void registerSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        _consumers.add(snmpMetricConsumer);

    }

    public void unRegisterSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        if (_consumers.contains(snmpMetricConsumer)) _consumers.remove(snmpMetricConsumer);

    }
}
