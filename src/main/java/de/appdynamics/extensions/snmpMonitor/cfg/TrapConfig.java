package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class TrapConfig {
    public void setOid(String oid) {
        _oid = oid;
    }

    private String _oid;

    public TrapConfig() {}
    public TrapConfig(String trapOid) {
        _oid = trapOid;

    }

    public String getOid() {
        return _oid;
    }
}
