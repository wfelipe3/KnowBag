FROM ubuntu/jre

RUN sudo apt-get install -y zookeeperd
RUN mkdir -p /root/Downloads
RUN wget "http://mirror.cc.columbia.edu/pub/software/apache/kafka/0.8.2.1/kafka_2.11-0.8.2.1.tgz" -O /root/Downloads/kafka.tgz
RUN mkdir -p /root/kafka
WORKDIR /root/kafka
RUN tar -xvzf /root/Downloads/kafka.tgz --strip 1
RUN pwd | ls
RUN echo 'advertised.host.name=192.168.99.100' >> /kafka/config/server.properties
RUN echo 'advertised.port=9092' >> /kafka/config/server.properties


CMD sh /etc/init.d/zookeeper start | nohup /root/kafka/bin/kafka-server-start.sh /root/kafka/config/server.properties > /root/kafka/kafka.log 2>&1 &