FROM jdk:1.8

RUN apt-get install -y zookeeperd
RUN mkdir -p /root/Downloads
RUN wget "http://apache.uniminuto.edu/kafka/0.10.1.1/kafka_2.11-0.10.1.1.tgz" -O /root/Downloads/kafka.tgz
RUN mkdir -p /root/kafka
WORKDIR /root/kafka
RUN tar -xvzf /root/Downloads/kafka.tgz --strip 1
RUN echo 'advertised.host.name=localhost' >> /root/kafka/config/server.properties
RUN echo 'advertised.port=9092' >> /root/kafka/config/server.properties


CMD sh /etc/init.d/zookeeper start ; nohup /root/kafka/bin/kafka-server-start.sh /root/kafka/config/server.properties
