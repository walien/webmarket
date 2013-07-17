#!/bin/bash

# Install
if [ "$1" == "install" ]; then
	echo "==================================================="
	echo "========== INSTALL STANDALONE WEBMARKET  =========="
	echo "==========       @walien - 2013          =========="
	echo "==================================================="
	cd ..
	mvn -Dmaven.test.skip=true clean install 
	cd install
	wget --output-document build/jetty-runner.jar http://repo2.maven.org/maven2/org/mortbay/jetty/jetty-runner/8.1.8.v20121106/jetty-runner-8.1.8.v20121106.jar
	echo "==================================================="
	echo "==================================================="
	echo "Install is done. Please launch the same script but replace the parameter 'install' by 'start'"
elif [ "$1" == "start" ]; then
	echo "==================================================="
	echo "==========  START STANDALONE WEBMARKET   =========="
	echo "==========       @walien - 2013          =========="
	echo "==================================================="
	export WEBMARKET_CONF=$PWD"/webmarket.conf"
	java -jar build/jetty-runner.jar build/webmarket-1.0.0-SNAPSHOT.war
else
	echo "Please run this script with :"
	echo "	- 'install' : if you want to install the standalone version of webmarket"
	echo "	- 'start' : if you want to run webmarket in standalone mode"
fi


