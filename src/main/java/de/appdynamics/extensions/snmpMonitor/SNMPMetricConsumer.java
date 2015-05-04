package de.appdynamics.extensions.snmpMonitor;

/**
 * Created by stefan.marx on 17.04.15.
 */
//TODO: Can this interface be implemented as a Abstract class
public interface SNMPMetricConsumer {

    /** will log a customer Trap metric
     *  CustomMetric|snmpTraps|trapName|metricName
     * @param trapName
     * @param metricName
     * @param metric
     */
    public void reportMetric(String trapName, String metricName, int metric);

    /** Will report a Trap hit (increase the trap counter by one! )
     *
     * @param name Trap Name
     */
    public void reportTrap(String name);
    public boolean isMachineAgentConsumer();
}
