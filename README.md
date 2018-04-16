1. Prerequisits:

	- Java version 8  or higher installed
	
	- SQLite installed
	
	- Kafka installed and running with default settings:
	
		on Windows
		
			a) <path-to-kafka-home>\bin\windows\zookeeper-server-start.bat config\zookeeper.properties 
			b) <path-to-kafka-home>\bin\windows\kafka-server-start.bat config\server.properties 
			
		on Linux
		
			a) <path-to-kafka-home>/bin/zookeeper-server-start.sh config/zookeeper.properties 
			b) <path-to-kafka-home>/bin/kafka-server-start.sh config/server.properties 
			
	- latest maven installed
	
2. Build

mvn clean package

mvn exec:java -Dexec.mainClass=speedcam.Demo 

3. Stopping demo by pressing Ctrl-C