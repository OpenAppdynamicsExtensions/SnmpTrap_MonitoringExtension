package de.appdynamics.extensions.snmpMonitor;

import java.io.FileNotFoundException;

/**
 * Created by stefan.marx on 21.04.15.
 */
public class AgentException extends Throwable {
    public AgentException(String msg, Throwable e) {
        super(msg,e);
    }
    public AgentException(String msg) {
        super(msg);
    }
}
