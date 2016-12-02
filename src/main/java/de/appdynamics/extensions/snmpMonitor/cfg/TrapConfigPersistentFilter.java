package de.appdynamics.extensions.snmpMonitor.cfg;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stefan.marx on 02.12.16.
 */
public class TrapConfigPersistentFilter {

    public ArrayList<FilterValue> getFilterElements() {
        return filter;
    }

    public void setFilterElements(ArrayList<FilterValue> filter) {
        this.filter = filter;
    }

    ArrayList<FilterValue> filter = new ArrayList<FilterValue>();


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    Integer defaultValue = new Integer(0);


    String oid;

    public void addFilter(String key, Integer i) {
        filter.add(new FilterValue(key,i));

    }

    public Integer getPersistentValue(PDU pdu) {

        if ( pdu.getVariable(new OID(getOid())) == null) return defaultValue;

        String text = pdu.getVariable(new OID(getOid())).toString();
        if (text == null || text.equals("")) return defaultValue;
        else {
            for (FilterValue fv: getFilterElements()) {
                if (fv.getFilter().equals(text)) return fv.getValue();
            }
            return defaultValue;
        }

    }
}
