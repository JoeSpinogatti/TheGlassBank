### Overview

This module is a driver for sending commands to a server from the command line.
This driver is to be used by developers who wish to test with the JSON format
that is sent from Google Glass to the server without having to have the actual
hardware.

### Usage
- Go to the `GoToCue` directory from the command line
- Run `./gradlew testcli:installApp`
- Once the build completes `cd` into `./testcli/build/install/testcli/bin`
- Run the `testcli` script

To package the command line application into an archive
- Run `./gradlew testcli:distZip`
- The archive will be `./testcli/build/distributions/testcli.zip`