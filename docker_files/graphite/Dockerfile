FROM ubuntu:14.04

RUN apt-get update 
RUN apt-get install -y wget
RUN apt-get install -y unzip 
WORKDIR /root
RUN wget https://github.com/obfuscurity/synthesize/archive/master.zip
RUN unzip master.zip
WORKDIR /root/synthesize-master/
RUN ./install


 
