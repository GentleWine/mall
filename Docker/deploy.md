# Docker deployment survey

### Overview

​	Docker is a kind of lightweight virtual technology, which makes development and deployment more portable.

​	We use docker as container, and launch our project packaged on its internal base. Expose host port 9000 mapping inner port 80, so that it can be reached across port 9000.

​	After containerized, push processed image onto our server registry. 

### Step 1. Containerize

​	Firstly, package our project and edit the Dockerfile.

```dockerfile
FROM openjdk:14
ARG JAR_FILE=target/alpha-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

​	We chose the openjdk as our base, copied our packaged project and named it as app.jar. 

​	Secondly, containerize our project with the help of Dockerfile. Use instruction: 

```shell
docker build -t spring-boot-docker .
```

Be care of there is a dot in the end which represents the destination of dockerfile. 

### Step 2. Create registry on server

​	Firstly, start the registry container on server:

```shell
docker run -dp 5000:5000 --restart=always --name registry registry:2
```

Notice that the registry is also a kind of container.

​	Secondly, configurate the local config of Docker engine.

```json
{
  "registry-mirrors": [],
  "insecure-registries": [
    "49.235.25.29:5000"
  ],
  "debug": false,
  "experimental": false,
  "features": {
    "buildkit": true
  }
}
```

Add insecure-registries with server location.

### Step3. Push & pull

​	Firstly, push local image to server registry.

```shell
docker image tag spring-boot-docker 49.235.25.29:5000/spring-boot-docker
docker image push 49.235.25.29:5000/spring-boot-docker
```

Tag it so that url can be resolved.

​	Secondly, pull it onto server from registry.

 ```shel
sudo docker pull localhost:5000/spring-boot-docker
 ```

Pay attention to "localhost".

​	Finally, run it!

```shell
sudo docker run -dp 9000:80 localhost:5000/spring-boot-docker
```

