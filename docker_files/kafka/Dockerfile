FROM jdk8

RUN apt-get install -y zookeeperd
RUN mkdir -p /root/Downloads
RUN wget "http://mirror.cc.columbia.edu/pub/software/apache/kafka/0.9.0.0/kafka_2.11-0.9.0.0.tgz" -O /root/Downloads/kafka.tgz
RUN mkdir -p /root/kafka
WORKDIR /root/kafka
RUN tar -xvzf /root/Downloads/kafka.tgz --strip 1
RUN pwd | ls
RUN echo 'advertised.host.name=localhost' >> /root/kafka/config/server.properties
RUN echo 'advertised.port=9092' >> /root/kafka/config/server.properties


CMD sh /etc/init.d/zookeeper start ; nohup /root/kafka/bin/kafka-server-start.sh /root/kafka/config/server.properties
