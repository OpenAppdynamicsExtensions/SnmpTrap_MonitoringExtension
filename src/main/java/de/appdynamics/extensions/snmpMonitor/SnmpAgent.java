package de.appdynamics.extensions.snmpMonitor;

import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpEndpointDefinition;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;

import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpAgent extends AManagedMonitor {

    SnmpAgent () {

    }

    @Override
    public TaskOutput execute(Map<String, String> map, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        // Read the local config File
        SnmpTrapMonitorConfig cfg = readConfig();

        // create and configure the SNMP Tap Listener based on the config
        SnmpTrapListener trapListener = new SnmpTrapListener(cfg);
        trapListener.start();

        // create SNMPMetricEvent consumer
        trapListener.registerSNMPMetricConsumer(new SNMPAgentMetricConsumer());


        do {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        } while (trapListener.isRunning());

        return new TaskOutput("Stopped");
    }

    private SnmpTrapMonitorConfig readConfig() {
        // TODO:Read a local file to create the cfg
        return createMockupConfiguration();
    }

    private SnmpTrapMonitorConfig createMockupConfiguration() {

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
}
