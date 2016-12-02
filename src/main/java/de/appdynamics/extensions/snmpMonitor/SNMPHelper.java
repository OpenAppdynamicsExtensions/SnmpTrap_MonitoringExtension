package de.appdynamics.extensions.snmpMonitor;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;

/**
 * Created by stefan.marx on 22.11.16.
 */
public class SNMPHelper {
    public static String buildBath(String path, PDU pdu) {
        String erg = "";
        String[] segments = path.split("##");
        for (String part : segments) {
            if (!part.matches("^\\d+(\\.\\d+)+$")) {
                erg += part;
            }  else {
                if (pdu.getVariable(new OID(part)) == null || pdu.getVariable(new OID(part)).toString().equals(""))
                    erg += "default";
                else
                    erg += pdu.getVariable(new OID(part));
            }
        }

        return erg;
    }
}
