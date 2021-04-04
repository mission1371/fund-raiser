"# fund-raiser" 


docker needs to be in swarm mode

docker compose runs server with local profile. 

before running compose you need to crease artifacts for the server and client projects.

to run all services
    docker-compose up
    
only for the postgres
      docker-compose up db-postgres
      
only for the server
    docker-compose up server
    
only for the client
      docker-compose up client

To update images and containers (e.g. init script for db changed) you can use `docker-compose up -d --build [service name, e.g db-oracle]`
