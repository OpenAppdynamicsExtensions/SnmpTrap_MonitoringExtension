package de.appdynamics.extensions.snmpMonitor;


import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.apache.log4j.Logger;
import org.snmp4j.PDU;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SNMPAgentMetricLogConsumer implements SNMPMetricConsumer {
    private Logger logger = Logger.getLogger(SNMPAgentMetricLogConsumer.class);

    public SNMPAgentMetricLogConsumer(){}

    @Override
    public void reportMetric(String trapName, String metricName, int metric) {

        logger.debug("Metrics Extracted: "+metricName+" from Trap "+trapName+" --> "+metric);
    }


    @Override
    public boolean isMachineAgentConsumer() {
        return false;
    }

    @Override
    public void reportTrap(TrapConfig cfg, PDU pdu) {
        logger.debug("Trap Received :"+ SNMPHelper.buildBath(cfg.getPath(),pdu));
        logger.debug("TRAP:"+pdu.toString());
    }
}
