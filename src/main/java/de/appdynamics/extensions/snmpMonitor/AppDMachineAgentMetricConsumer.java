package de.appdynamics.extensions.snmpMonitor;

import com.singularity.ee.agent.systemagent.api.MetricWriter;

import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.apache.log4j.Logger;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;


/**
 * Created by rajatlocal on 27/04/15.
 */
public class AppDMachineAgentMetricConsumer implements SNMPMetricConsumer{

    private static final String METRIC_PREFIX = "Custom Metrics|SNMPTraps";
    private static final String METRIC_SEP = "|";
    public static final String EVENTS = "events";
    private SnmpAgent _snmpAgent;
    private static Logger logger = Logger.getLogger(SNMPMetricConsumer.class);
    public AppDMachineAgentMetricConsumer(SnmpAgent snmpAgent) {

        _snmpAgent = snmpAgent;
    }

    @Override
    public synchronized void reportMetric(String trapName, String metricName, int metric) {
       // TODO: Implement the metrics reporting code
    }



    @Override
    public boolean isMachineAgentConsumer() {
        return true;
    }

    @Override
    public void reportTrap(TrapConfig cfg, PDU pdu) {

        String fullPath = getEventsMetricPath(SNMPHelper.buildBath(cfg.getPath(), pdu));
        if (cfg.isPersistent()) {
            PersistentCache pCache = PersistentCache.getDefault();
            Integer oldValue = pCache.savePersistentValue(fullPath
                    , cfg.getPersistentValue());

            if (oldValue != null && oldValue.equals(cfg.getPersistentValue())) {
                logger.debug("Persistent Value allready set : "+fullPath + " == "+ oldValue);

            } else {
                logger.debug("Value Set :"+fullPath+" -- "+cfg.getPersistentValue());
            }





        } else {
            MetricWriter writer = _snmpAgent.getMetricWriter(fullPath,
                    MetricWriter.METRIC_AGGREGATION_TYPE_SUM,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_SUM,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE);
            logger.debug("Uploading metric to controller: " + fullPath);
            writer.printMetric("1");
        }
    }


    private String getEventsMetricPath(String trapName) {
        return METRIC_PREFIX + METRIC_SEP + trapName + METRIC_SEP + EVENTS;
    }


}
