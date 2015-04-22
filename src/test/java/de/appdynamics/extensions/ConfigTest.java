package de.appdynamics.extensions;

import de.appdynamics.extensions.snmpMonitor.AgentException;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpEndpointDefinition;
import de.appdynamics.extensions.snmpMonitor.cfg.SnmpTrapMonitorConfig;
import de.appdynamics.extensions.snmpMonitor.cfg.TrapConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by stefan.marx on 21.04.15.
 */
public class ConfigTest {
    public static void main(String[] args) {

        writeConfig();

        try {
            SnmpTrapMonitorConfig cfg = readConfig();
            System.out.println(cfg);
            System.out.println(cfg.getEndpointConfig().getListenerPort());
        } catch (AgentException e) {
            e.printStackTrace();
        }


    }

    private static SnmpTrapMonitorConfig readConfig() throws AgentException {

        SnmpTrapMonitorConfig cfg = null;
        Yaml yaml = new Yaml();
        try {

            cfg = (SnmpTrapMonitorConfig) yaml.load(new FileInputStream("test.yml"));



        } catch (FileNotFoundException e) {
            throw new AgentException("ConfigFile not found!",e);
        }
        return cfg;
    }

    private static void writeConfig() {
        SnmpTrapMonitorConfig cfg = createMockupConfiguration();

        final DumperOptions options = new DumperOptions();

        options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(new SafeConstructor(), new Representer(), options);

        String output = yaml.dump(cfg);
        System.out.println(output);

    }


    private static SnmpTrapMonitorConfig createMockupConfiguration() {

        SnmpTrapMonitorConfig cfg = new SnmpTrapMonitorConfig();

        // SNMP Trap End Point
        SnmpEndpointDefinition ep = new SnmpEndpointDefinition();
        ep.setListenerPort(1026);
        ep.setListenerAddress("0.0.0.0");
        ep.setSNMPv1Enable(true);
        ep.setSNMPV2Enable(true);
        ep.setSNMPV3Enable(false);
        cfg.setEndpointConfig(ep);

        TrapConfig trap = new TrapConfig("1.2.3.2.3.4.2.3.4.2.1.2500", "Met2500");
        cfg.addTrapConfig(trap);


        trap = new TrapConfig("1.2.3.2.3.4.2.3.4.2.1.2501" , "Met2501");
        cfg.addTrapConfig(trap);


        trap = new TrapConfig("1.2.3.2.3.4.2.3.4.2.1.2502" , "Met2502");
        cfg.addTrapConfig(trap);

        return cfg;
    }
}
