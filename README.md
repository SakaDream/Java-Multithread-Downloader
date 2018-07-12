# Java Multithread Downloader

A simple multithread downloader written in Java

![Demo Screenshot](https://puu.sh/AVdGT.gif)

## Build

`mvn clean package`

## Run

`java -jar target\multithread-downloader-1.0-SNAPSHOT-jar-with-dependencies.jar <download link>`

### Options

- `-c`, `--connections` : Number of connections / threads
- `-l`, `--location` : Downloads location

## To-do

- ~~Number of connections / threads and Downloads folder location options~~
- ~~Progress Monitor~~
- `download` command
- `set` configuration command (Number of connections, Downloads location)