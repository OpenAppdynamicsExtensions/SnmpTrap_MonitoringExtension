package de.appdynamics.extensions.snmpMonitor;


import org.apache.log4j.Logger;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SNMPAgentMetricConsumer implements SNMPMetricConsumer {
    private Logger logger = Logger.getLogger(SNMPAgentMetricConsumer.class);

    public SNMPAgentMetricConsumer(){}

    @Override
    public void reportMetric(String trapName, String metricName, int metric) {

    }

    @Override
    public void reportTrap(String trapMessage) {
        logger.debug(trapMessage);
    }

    @Override
    public boolean isMachineAgentConsumer() {
        return false;
    }
}
