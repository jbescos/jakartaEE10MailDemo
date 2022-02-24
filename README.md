# Jakarta Mail - Angus Demo

This demo shows a simple example about:
* How to send one email with SMTP protocol.
* How to access the inbox folder with IMAP protocol.

It is divided in 2 parts:
* Start the mail server.
* Run the demo.

## Start the mail server

The mail server is going to run in a Docker instance, so you have to make sure that you have Docker installed and running.

To install the server you need to execute the script: [./setup.sh](setup.sh)

You can read the logs of the mail server connecting to the docker instance in this way:

    docker exec -it mail-demo /bin/bash

And then check the log:

    /root/mailserver.log

## Run the demo

This small java application will connect to the mail server. You need to make sure that the mail server is running.

You can use the next Docker command to check this:

    $ docker ps -a
    CONTAINER ID   IMAGE         COMMAND                   CREATED      STATUS          PORTS                                                                                                                                                                                                                                 NAMES
    61a6d2ec0fce   mail-server   "/bin/sh -c '\"/tmp/d…"   7 days ago   Up 18 minutes   25/tcp, 110/tcp, 143/tcp, 465/tcp, 587/tcp, 993/tcp, 0.0.0.0:18000->1025/tcp, 0.0.0.0:18004->1143/tcp, 0.0.0.0:18003->1465/tcp, 0.0.0.0:18005->1587/tcp, 0.0.0.0:18001->1993/tcp, 0.0.0.0:18002->8000/tcp, 0.0.0.0:18006->37809/tcp   mail-demo

Then you have to compile the demo with Maven:

    $ mvn clean package

And execute it in this way:

    $ java -jar target/demo10-1.0.jar

In case you are having any issue with the mail server, you can uncomment the line of [Main](src/main/java/jakarta/mail/demo/Main.java)

    session.setDebug(true);

