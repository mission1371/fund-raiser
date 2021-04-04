# Fund Raiser 

    For detailed information refer to dedicated readme files under `server` and `client` folders.

### Docker configuration
In order for containers to communicate with each other docker needs to be running with swarm mode.

There is a `docker-compose.yml` file which orchestrates the containers for the application. 
Once compose file run with `docker-compose up` it will build and run **Postgres**, **Server** and **Client** containers.

### Before running compose file!
Each sub project needs to be build before compose file runs properly. Otherwise, there won't be any artifact to put it into containers. 

- To build `Spring project` run under `server/` folder
    

    gradlew clean build

- To build `Angular project` run under `client/` folder
    
    
    npm install
    npm run prod-build    
 
`npm install` step can be skipped after initial successful installation

After the installations run below command to start everything under `fund-raiser` folder where docker-compose.yml exist 

    docker-compose up
