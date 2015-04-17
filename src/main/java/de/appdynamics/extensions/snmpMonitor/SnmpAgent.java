package de.appdynamics.extensions.snmpMonitor;

import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;

import java.util.Map;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpAgent extends AManagedMonitor {


    @Override
    public TaskOutput execute(Map<String, String> map, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        return null;
    }
}
