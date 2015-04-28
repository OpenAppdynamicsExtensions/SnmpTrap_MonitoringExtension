package de.appdynamics.extensions.snmpMonitor;

import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;


/**
 * Created by rajatlocal on 27/04/15.
 */
public class AppDMachineAgentMetricConsumer implements SNMPMetricConsumer{

    private static final String metricPrefix = "Custom Metrics|SNMP|";

    @Override
    public void reportMetric(String trapName, String metricName, int metric) {
        //TODO: Change implementation after talking to Stefan
        MetricWriter met = new SnmpAgent().getMetricWriter(metricPrefix + metricName , MetricWriter.METRIC_AGGREGATION_TYPE_SUM);
        met.printMetric("1");
    }

    @Override
    public void reportTrap(String name) {

    }

    @Override
    public boolean isMachineAgentConsumer() {
        return true;
    }
}
