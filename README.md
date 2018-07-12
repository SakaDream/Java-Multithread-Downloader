# Java Multithread Downloader

A simple multithread downloader written in Java

![Demo Screenshot](https://puu.sh/AVdGT.gif)

## Build

`mvn clean package`

## Run

`java -jar target\multithread-downloader-1.0-SNAPSHOT-jar-with-dependencies.jar <download link> <options>`


## Download and Run Release Software
You can download releases in this [link](https://github.com/SakaDream/Java-Multithread-Downloader/releases)

Run the release: `java -jar multithread-downloader.jar <download link> <options>`

## Options

- `-c`, `--connections` : Number of connections / threads
- `-l`, `--location` : Downloads location

## To-do

- ~~Number of connections / threads and Downloads folder location options~~
- ~~Progress Monitor~~
- `download` command
- `set` configuration command (Number of connections, Downloads location)

## Credits

- `HttpStatusCode.java` by Peter Pilgrim - http://www.xenonique.co.uk/blog/2013/11/27/my-http-response-java-enumeration-type-contracting/