# 使用官方的 Java 镜像作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 定义构建参数
ARG JAR_FILE

# 将 JAR 文件复制到容器中
COPY ${JAR_FILE} app.jar

# 运行 Java 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
