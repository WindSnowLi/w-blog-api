FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

LABEL "author"="WindSnowLi"
LABEL "version"="1.0.1"
LABEL "email"="windsnowli@163.com"

ADD w-blog-1.0.1.jar w-blog.jar

# 配置环境变量支持中文
ENV LANG="en_US.UTF-8"

# 环境配置文件
ENV active="dev"

EXPOSE 8888
EXPOSE 9999

ENTRYPOINT ["sh","-c","java -jar w-blog.jar --spring.profiles.active=$active"]