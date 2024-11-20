FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 动态传递 JAR 文件路径
ARG JAR_FILE=target/app.jar

# 复制 JAR 文件到镜像中
COPY ${JAR_FILE} app.jar

# 配置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]