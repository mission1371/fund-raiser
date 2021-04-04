FROM openjdk:11.0.10-jdk

ENV APP_PROFILE=""
ENV ARTIFACT=fund-raiser-server-0.0.1-SNAPSHOT.jar
ENV APP_PATH=opt/app-root/src

COPY build/libs/${ARTIFACT} ${APP_PATH}/${ARTIFACT}
COPY image-repository ${APP_PATH}/image-repository

ENTRYPOINT java -jar "-Dspring.profiles.active=${APP_PROFILE}" "${APP_PATH}/${ARTIFACT}"
