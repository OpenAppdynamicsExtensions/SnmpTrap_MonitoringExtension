package de.appdynamics.extensions;

import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import de.appdynamics.extensions.snmpMonitor.SnmpAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class LocalTest {

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
        t.setTaskDir("./src/main/config");
        try {
            agent.execute(map,t);
        } catch (TaskExecutionException e) {
            e.printStackTrace();
        }
    }
}
