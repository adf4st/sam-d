# SAM-D Starter App

A starter kit for building applications with the SAM-D stack  
**Java SpringBoot | Angular.js | MongoDB** all running in **Docker** containers

[See the live demo!](http://samd.alexfabian.net)

## How it Works
![architecture diagram](http://samd.alexfabian.net/img/system_diagram.png)

## Getting Started 

#### Starting the Stack with Docker-Compose from base images
The docker-compose.yml file in the root directory starts a mongodb, api, and web container from the starter SAM-D images on [Docker Hub](https://hub.docker.com/r/afabian). Start the application in

1. make sure you have Docker properly installed and started  
1. download the docker-compose to a local directory
2. from that directory, run `docker-compose up`

#### Starting the development stack with Docker-Compose 
The docker-compose-build.yml in the root directory starts a mongodb, api, and web container from fresh images built from the Dockerfiles in the `/web` and `/api` root directories. Use this docker compose file if you've made changes to the web or api and want to run the stack and see your changes.

1. make sure you have Docker properly installed and started  
1. download or clone the sam-d git repo 
2. make desired changes to the web or api projects
3. follow build/compile instructions in READMEs of the web and api projects
4. from the root sam-d directory run `docker-compose -f docker-compose-build.yml up`

## Demo Deployment
The SAM-D applicaiton stack demo is deployed on Amazon Elastic Container Service (ECS). Creating Docker containers for each layer makes it easy to deploy the entire stack on any hosting or platform that supports Docker.