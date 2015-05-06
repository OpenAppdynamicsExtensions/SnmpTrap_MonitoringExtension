package de.appdynamics.extensions.snmpMonitor;


import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        try {
            //Read the local config file
            cfg = readConfig(taskExecutionContext.getTaskDir());

            // create and configure the SNMP Tap Listener based on the config
            SnmpTrapListener trapListener = new SnmpTrapListener(cfg);
            trapListener.start();

            // create SNMPMetricEvent consumers
            trapListener.registerSNMPMetricConsumer(new AppDMachineAgentMetricConsumer(this));
            trapListener.registerSNMPMetricConsumer(new SNMPAgentMetricLogConsumer());

            do {
                Thread.sleep(60000);
            } while (trapListener.isRunning());
        }
        catch (AgentException e) {
            logger.error("SNMPTrapMonitor Error :: " +e.getMessage(),e);
            return new TaskOutput("ERROR");
        }
        catch (InterruptedException e) {
        }
        return new TaskOutput("Stopped");
    }

    /**
     * Reads trap configuration from config.yml
     * @return
     * @throws AgentException
     * @param taskDir
     */
    private SnmpTrapMonitorConfig readConfig(String taskDir) throws AgentException {
       Yaml yaml = new Yaml();
        SnmpTrapMonitorConfig cfg = null;

        try {
            //logger.debug(taskDir);
            File dir = new File(taskDir);
            cfg = (SnmpTrapMonitorConfig) yaml.load(new FileInputStream(new File(dir,"config.yml")));

        } catch (FileNotFoundException e) {
            throw new AgentException("ConfigFile not found!",e);
        }
        return cfg;
    }
}
