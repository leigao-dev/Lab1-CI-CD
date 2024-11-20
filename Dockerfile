FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 动态传递 JAR 文件路径
ARG JAR_FILE=Lab1-CI-CD-0.0.1-SNAPSHOT.jar

# 复制 JAR 文件到镜像中
#COPY ${JAR_FILE} app.jar
RUN echo "JAR_FILE is: ${JAR_FILE}"


COPY /home/runner/work/Lab1-CI-CD/Lab1-CI-CD/Lab1-CI-CD-0.0.1-SNAPSHOT.jar app.jar


# 配置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]