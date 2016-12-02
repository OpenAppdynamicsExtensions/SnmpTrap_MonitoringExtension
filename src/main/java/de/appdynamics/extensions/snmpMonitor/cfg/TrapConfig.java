package de.appdynamics.extensions.snmpMonitor.cfg;

import org.snmp4j.PDU;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class TrapConfig {

    private String _oid;
    private String _path;
    private Integer persistentValue;

    public TrapConfigPersistentFilter getPersistentFilter() {
        return persistentFilter;
    }

    public void setPersistentFilter(TrapConfigPersistentFilter persistentFilter) {
        this.persistentFilter = persistentFilter;
    }

    private TrapConfigPersistentFilter persistentFilter;

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

    public Integer getPersistentValue(PDU pdu) {
        if (persistentValue != null) {
            return persistentValue;
        } else {
            if (persistentFilter != null) return getPersistentFilter().getPersistentValue(pdu);

        }
        return null;
    }

    public void setPersistentValue(Integer persistentValue) {
        this.persistentValue = persistentValue;
    }

    public boolean isPersistent() {
        return (persistentValue!=null || persistentFilter != null);
    }

    @Override
    public String toString() {
        return "- :"+_oid+"  --> "+_path+
                ((isPersistent())?"  Persistent ("+persistentValue+")":"");
    }
}
