# syntax = docker/dockerfile:1.0-experimental
FROM hseeberger/scala-sbt:8u181_2.12.8_1.2.8
COPY . /
WORKDIR /
RUN --mount=type=cache,target=/root/.ivy2 --mount=type=cache,target=/root/.sbt sbt -ivy ./.ivy/ ";clean; test; set version:= \"$VERSION\";clean;assembly"

FROM openjdk:8-jre-alpine
RUN mkdir /app
COPY --from=0 target/scala-2.12/assembly.jar /app/assembly.jar
ADD runAppInDocker.sh /app
WORKDIR app

CMD ["sh", "./runAppInDocker.sh"]