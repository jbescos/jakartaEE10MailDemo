FROM jakartaee/cts-mailserver:0.1

ENV MAIL_USER="user01@james.local"
ENV MAIL_HOST="localhost"

ADD conf/* /root/conf
ADD dockerScript.sh /tmp/dockerScript.sh
RUN chmod -v +x /tmp/dockerScript.sh
# RUN sh /tmp/dockerScript.sh

EXPOSE 1025/tcp
EXPOSE 1993/tcp
EXPOSE 8000/tcp
EXPOSE 1465/tcp
EXPOSE 1143/tcp
EXPOSE 1587/tcp
EXPOSE 37809/tcp
EXPOSE 1110/tcp

ENTRYPOINT "/tmp/dockerScript.sh" && "/bin/bash"
