#!/bin/bash

controllerKey=$1
controllerHost=$2
controllerPort=$3

JAVA_OPTIONS="-Dappdynamics.agent.applicationName=SNMPTrapMonitor
-Dappdynamics.controller.hostName=${controllerHost-172.199.0}
-Dappdynamics.controller.port=${controllerPort-8090}
-Dappdynamics.agent.tierName=SNMP
-Dappdynamics.agent.uniqueHostId=SNMP1
-Dappdynamics.agent.nodeName=NodeUTIL
-Dappdynamics.agent.uniqueHostId=SNMPSandbox
-Dappdynamics.agent.accountAccessKey=${controllerKey}
-Dappdynamics.agent.accountName=customer1
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

java $JAVA_OPTIONS -jar ./build/sandbox/agent/machineagent.jar &
agentPid=$!

sleep 10
echo started agent with PID:$agentPid
sleep 2

less +F build/sandbox/agent/logs/machine-agent.log

echo kill agent
kill -9 $agentPid

sleep 1
echo DONE