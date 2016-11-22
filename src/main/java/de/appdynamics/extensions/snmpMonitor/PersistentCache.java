package de.appdynamics.extensions.snmpMonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by stefan.marx on 22.11.16.
 */
public class PersistentCache {
    private static PersistentCache _defaultInstance;

    private Map<String,Integer> _map = new HashMap<String,Integer>();

    public static PersistentCache getDefault() {
        if (_defaultInstance == null) _defaultInstance = new PersistentCache();

        return _defaultInstance;


    }

    public Integer savePersistentValue(String eventsMetricPath,Integer value) {
        Integer oldValue = null;
        if (_map.containsKey(eventsMetricPath)) oldValue = _map.get(eventsMetricPath);

        _map.put(eventsMetricPath,value);

        return oldValue;
    }

    public Set<Map.Entry<String, Integer>> getEntries() {
        return _map.entrySet();

    }
}
