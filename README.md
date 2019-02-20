# CDR-MiS 

## Building

Full Build
 
    ./gradlew build
   
Quick Build
 
    ./gradlew build -x test
    
Just Tests
    
    ./gradlew test

## Running

### Setup Docker Networks

Ensure that the docker network metadata-server is setup by running:

    docker network ls

If not add run the following command:
```
docker network create -d overlay metadata-server
```

### Docker Swarm

To deploy as a swarm stack run:

    ./gradlew run 
    
or alternatively:

    docker stack deploy -c deployment/build/docker-compose.yml ohana

### Logging

To view logs for the running docker container run 
    
    docker container ls
    docker logs <container> -f


### Running on a different platform
1. Remove `EXPOSE 9090` from `metadata-server/Dockerfile`

2. Add the following to the docker-compose.yml file:


    networks:
    . . .
        
        metadata-server:
            external: true
           
    services:
    . . .
        metadata:
            image: metadata-server
            networks:
                default: null
                metadata-server: null
            
