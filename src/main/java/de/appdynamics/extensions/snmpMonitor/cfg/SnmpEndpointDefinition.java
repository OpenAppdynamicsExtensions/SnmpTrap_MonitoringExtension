package de.appdynamics.extensions.snmpMonitor.cfg;

/**
 * Created by stefan.marx on 17.04.15.
 */
public class SnmpEndpointDefinition {
    private int _listenerPort;
    private String _listenerAddress;
    private boolean _SNMPv1Enable;
    private boolean _SNMPV2Enable;
    private boolean _SNMPV3Enable;

    public void setListenerPort(int listenerPort) {
        _listenerPort = listenerPort;
    }

    public int getListenerPort() {
        return _listenerPort;
    }

    public void setListenerAddress(String listenerAddress) {
        _listenerAddress = listenerAddress;
    }

    public String getListenerAddress() {
        return _listenerAddress;
    }

    public void setSNMPv1Enable(boolean SNMPv1Enable) {
        _SNMPv1Enable = SNMPv1Enable;
    }

    public boolean isSNMPv1Enable() {
        return _SNMPv1Enable;
    }

    public void setSNMPV2Enable(boolean SNMPV2Enable) {
        _SNMPV2Enable = SNMPV2Enable;
    }

    public boolean isSNMPV2Enable() {
        return _SNMPV2Enable;
    }

    public void setSNMPV3Enable(boolean SNMPV3Enable) {
        _SNMPV3Enable = SNMPV3Enable;
    }

    public boolean isSNMPV3Enable() {
        return _SNMPV3Enable;
    }
}
