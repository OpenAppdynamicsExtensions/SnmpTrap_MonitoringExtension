package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class TrapConfig {
    private final String _trapOid;
    private String _oid;

    public TrapConfig(String trapOid) {
        _trapOid = trapOid;

    }

    public String getOid() {
        return _oid;
    }
}
