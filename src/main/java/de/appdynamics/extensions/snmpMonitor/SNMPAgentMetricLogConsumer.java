package de.appdynamics.extensions.snmpMonitor;


import org.apache.log4j.Logger;

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
    public void reportTrap(String trapMessage) {
        logger.debug("Trap Received :"+ trapMessage);
    }

    @Override
    public boolean isMachineAgentConsumer() {
        return false;
    }
}
