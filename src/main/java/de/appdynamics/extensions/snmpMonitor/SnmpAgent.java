package de.appdynamics.extensions.snmpMonitor;


import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpEndpointDefinition;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpAgent extends AManagedMonitor {
    private static Logger logger = Logger.getLogger(SnmpAgent.class);

    /**
     * Empty constructor
     */
    public SnmpAgent () {

    }

    @Override
    public TaskOutput execute(Map<String, String> map, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        // Local config file place holder
        SnmpTrapMonitorConfig cfg = null;

        //Read the local config file
        try {
            cfg = readConfig();
        } catch (AgentException e) {
            logger.error("Config not found :: "+e.getMessage(),e);
            return new TaskOutput("ERROR");
        }

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

    /**
     * Reads trap configuration from config.yml
     * @return
     * @throws AgentException
     */
    private SnmpTrapMonitorConfig readConfig() throws AgentException {
       Yaml yaml = new Yaml();
        SnmpTrapMonitorConfig cfg = null;

        try {
            cfg = (SnmpTrapMonitorConfig) yaml.load(new FileInputStream("config.yml"));
            //logger.debug(message.get("message"));
        } catch (FileNotFoundException e) {
            throw new AgentException("ConfigFile not found!",e);
        }
        return cfg;
    }
}
