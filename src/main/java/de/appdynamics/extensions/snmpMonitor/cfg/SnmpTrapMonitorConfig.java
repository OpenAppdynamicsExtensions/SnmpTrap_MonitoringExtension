package de.appdynamics.extensions.snmpMonitor.cfg;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpTrapMonitorConfig {
    public Map<String, TrapConfig> getTraps() {
        return _traps;
    }

    public void setTraps(Map<String, TrapConfig> traps) {
        _traps = traps;
    }

    private Map<String, TrapConfig> _traps = new Hashtable<String, TrapConfig>();
    private SnmpEndpointDefinition _endpointConfig;

    public void addTrapConfig(TrapConfig trap) {
        if (!_traps.containsKey(trap.getOid())) {
            _traps.put(trap.getOid(),trap);
        }

    }

    public void setEndpointConfig(SnmpEndpointDefinition endpointConfig) {
        _endpointConfig = endpointConfig;
    }

    public SnmpEndpointDefinition getEndpointConfig() {
        return _endpointConfig;
    }
}
