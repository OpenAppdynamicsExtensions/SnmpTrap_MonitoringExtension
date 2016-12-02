package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 02.12.16.
 */
public class FilterValue {
    String filter;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    Integer value;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


    public  FilterValue() {}
    public  FilterValue(String key,Integer v) {
        this.filter = key;
        this.value = v;
    }

}

