# pingserver

## Run Locally

The server logs the requests that it receives and returns a 200 OK response. 

`sbt run`

`curl -v --data "data" http://localhost:9999/path`

## Build Docker Image

`sbt clean universal:packageZipTarball`

`docker build .`