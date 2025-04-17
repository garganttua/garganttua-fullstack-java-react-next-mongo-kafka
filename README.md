# showcase-site

#### Install a Local MongoDB Database with Docker
1. Install Docker.
2. In a shell:
   - Execute command: **docker pull mongo**.
   - Execute command: **docker run --name mongo -p 27017:27017 -d mongo**.
   - Execute command: **docker container list**.
   - Execute command: **docker exec -it ${container_ID} mongosh**.
   - Execute command: **use garganttua-showcase-site**.
   - Execute command: **db.createUser({user:"backend",pwd:"backend",roles:[{role:"userAdminAnyDatabase", db:"admin"}]})**.
   - Quit MongoDB shell with **CTRL-C**.