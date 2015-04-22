package de.appdynamics.extensions;

import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import de.appdynamics.extensions.snmpMonitor.SNMPAgentMetricConsumer;
import de.appdynamics.extensions.snmpMonitor.SnmpAgent;
import de.appdynamics.extensions.snmpMonitor.SnmpTrapListener;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpEndpointDefinition;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class LocalTest {
    private static SnmpTrapMonitorConfig createMockupConfiguration() {

        SnmpTrapMonitorConfig cfg = new SnmpTrapMonitorConfig();

        // SNMP Trap End Point
        SnmpEndpointDefinition ep = new SnmpEndpointDefinition();
        ep.setListenerPort(9999);
        ep.setListenerAddress("0.0.0.0");
        ep.setSNMPv1Enable(true);
        ep.setSNMPV2Enable(true);
        ep.setSNMPV3Enable(false);

        cfg.setEndpointConfig(ep);

        TrapConfig trap = new TrapConfig(".1.2.3.2.3.4.2.3.4.2.1.2500");
        cfg.addTrapConfig(trap);


        trap = new TrapConfig(".1.2.3.2.3.4.2.3.4.2.1.2501");
        cfg.addTrapConfig(trap);


        trap = new TrapConfig(".1.2.3.2.3.4.2.3.4.2.1.2502");
        cfg.addTrapConfig(trap);

        return cfg;
    }

    public static void main(String[] args) {
       /* // Read the local config File
        SnmpTrapMonitorConfig cfg = createMockupConfiguration();

        // create and configure the SNMP Tap Listener based on the config
        SnmpTrapListener trapListener = new SnmpTrapListener(cfg);
        trapListener.start();

        // create SNMPMetricEvent consumer
        trapListener.registerSNMPMetricConsumer(new SNMPAgentMetricConsumer());*/

        //Test the execute method of SNMP agent
        SnmpAgent agent = new SnmpAgent();
        Map <String, String> map = new HashMap<String, String>();
        TaskExecutionContext t = new TaskExecutionContext();
        try {
            agent.execute(map,t);
        } catch (TaskExecutionException e) {
            e.printStackTrace();
        }
    }
}
