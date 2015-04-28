package de.appdynamics.extensions.snmpMonitor;

/**
 * Created by stefan.marx on 17.04.15.
 */
//TODO: Can this interface be implemented as a Abstract class
public interface SNMPMetricConsumer {

    public void reportMetric(String trapName, String metricName, int metric);
    public void reportTrap(String name);
    public boolean isMachineAgentConsumer();
}
