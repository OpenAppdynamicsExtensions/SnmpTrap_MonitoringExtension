package de.appdynamics.extensions.snmpMonitor;

import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.snmp4j.PDU;

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


    public boolean isMachineAgentConsumer();

    public void reportTrap(TrapConfig cfg, PDU pdu);
}
