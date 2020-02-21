FROM nimmis/java-centos:oracle-8-jre

RUN yum update -y 
RUn yum install xorg-x11-server-Xvfb which  libXext libXrender libXtst libfontconfig.so.1 -y

COPY osBot.jar /root/osBot.jar
COPY OSBot /root/OSBot
COPY entrypoint.sh /root/entrypoint.sh 
RUN chmod +x /root/entrypoint.sh






ENTRYPOINT ["/root/entrypoint.sh"]
