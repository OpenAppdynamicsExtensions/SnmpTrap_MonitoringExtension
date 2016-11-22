package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class TrapConfig {

    private String _oid;
    private String _path;
    private Integer persistentValue;

    public TrapConfig() {}
    public TrapConfig(String trapOid, String trapPath) {
        _oid = trapOid;
        setPath(trapPath);
    }

    public String getOid() {
        return _oid;
    }

    public void setOid(String oid) {
        _oid = oid;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        this._path = path;
    }

    public Integer getPersistentValue() {
        return persistentValue;
    }

    public void setPersistentValue(Integer persistentValue) {
        this.persistentValue = persistentValue;
    }

    public boolean isPersistent() {
        return persistentValue!=null;
    }
}
