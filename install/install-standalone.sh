#!/bin/sh

# Install

wget --output-document build/jetty-runner.jar http://repo2.maven.org/maven2/org/mortbay/jetty/jetty-runner/8.1.8.v20121106/jetty-runner-8.1.8.v20121106.jar
java -jar build/jetty-runner.jar build/webmarket-1.0.0-SNAPSHOT.war

# Start


