package de.appdynamics.extensions.snmpMonitor;

import com.singularity.ee.agent.commonservices.metricgeneration.metrics.MetricAggregatorType;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.controller.api.constants.MetricClusterRollupType;
import com.singularity.ee.controller.api.constants.MetricTimeRollupType;


/**
 * Created by rajatlocal on 27/04/15.
 */
public class AppDMachineAgentMetricConsumer implements SNMPMetricConsumer{

    private static final String metricPrefix = "Custom Metrics|SNMPTraps|";
    private static final String METRIC_SEP = "|";
    public static final String EVENTS = "events";
    private SnmpAgent _snmpAgent;

    public AppDMachineAgentMetricConsumer(SnmpAgent snmpAgent) {

        _snmpAgent = snmpAgent;
    }

    @Override
    public synchronized void reportMetric(String trapName, String metricName, int metric) {
       // TODO: Implement the metrics reporting code
    }

    @Override
    public synchronized void reportTrap(String name) {
        MetricWriter writer = _snmpAgent.getMetricWriter(getEventsMetricPath(name), MetricAggregatorType.SUM.toString(),
                MetricTimeRollupType.SUM.toString(), MetricClusterRollupType.COLLECTIVE.toString());
        writer.printMetric("1");
    }


    @Override
    public boolean isMachineAgentConsumer() {
        return true;
    }


    private String getEventsMetricPath(String trapName) {
        return metricPrefix+METRIC_SEP+trapName+METRIC_SEP+ EVENTS;
    }
}
