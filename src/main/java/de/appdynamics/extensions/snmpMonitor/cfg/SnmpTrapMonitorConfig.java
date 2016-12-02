package de.appdynamics.extensions.snmpMonitor.cfg;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpTrapMonitorConfig {

    private Map<String, TrapConfig> _traps = new Hashtable<String, TrapConfig>();
    private SnmpEndpointDefinition _endpointConfig;

    public Map<String, TrapConfig> getTraps() {
        return _traps;
    }

    public void setTraps(Map<String, TrapConfig> traps) {
        _traps = traps;
    }



    public SnmpEndpointDefinition getEndpointConfig() {
        return _endpointConfig;
    }

    public void setEndpointConfig(SnmpEndpointDefinition endpointConfig) {
        _endpointConfig = endpointConfig;
    }

    public void addTrapConfig(TrapConfig trap) {
        if (!_traps.containsKey(trap.getOid())) {
            _traps.put(trap.getOid(),trap);
        }
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer("SNMPTrapConfig");
        b.append(_endpointConfig)
                .append("\nTraps:\n");
        for (TrapConfig tc : getTraps().values()) {
            b.append(tc).append("\n");
        }
        return b.toString();
    }
}
