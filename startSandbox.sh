#!/bin/bash
JAVA_OPTIONS="-Dappdynamics.agent.applicationName=SNMPTrapMonitor
-Dappdynamics.controller.hostName=${controllerHost-172.199.0.5}
-Dappdynamics.controller.port=${controllerPort-8090}
-Dappdynamics.agent.tierName=SNMP
-Dappdynamics.agent.nodeName=NodeUTIL
-Dappdynamics.agent.uniqueHostId=SNMPSandbox
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

java $JAVA_OPTIONS -jar ./build/sandbox/agent/machineagent.jar
