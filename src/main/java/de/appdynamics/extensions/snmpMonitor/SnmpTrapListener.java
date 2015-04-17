package de.appdynamics.extensions.snmpMonitor;

import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpTrapListener {
    private final SnmpTrapMonitorConfig _cfg;
    private Set<SNMPMetricConsumer> _consumers = new HashSet<SNMPMetricConsumer>();

    public boolean isRunning() {
        return _running;
    }

    private boolean _running;

    public SnmpTrapListener(SnmpTrapMonitorConfig cfg) {
        _cfg = cfg;

    }

    /** starts the SNMP Trap receiver and will start listening for Events comming in
     *
     */
    public void start() {
        _running =true;
        try {
            //TODO :


        }   finally {
            _running =false;
        }


    }

    public void registerSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        _consumers.add(snmpMetricConsumer);

    }

    public void unRegisterSNMPMetricConsumer(SNMPMetricConsumer snmpMetricConsumer) {
        if (_consumers.contains(snmpMetricConsumer)) _consumers.remove(snmpMetricConsumer);

    }

}
