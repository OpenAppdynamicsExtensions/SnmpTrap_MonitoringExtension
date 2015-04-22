package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class TrapConfig {

    private String _oid;
    private String _name;

    public TrapConfig() {}
    public TrapConfig(String trapOid, String trapName) {
        _oid = trapOid;
        set_name(trapName);
    }

    public String getOid() {
        return _oid;
    }

    public void setOid(String oid) {
        _oid = oid;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
