#!/bin/bash

# docker exec -it mail-demo /bin/bash

cd /root 
/root/startup.sh | tee /root/mailserver.log &
sleep 120
bash -x /root/create_users.sh 2>&1 | tee /root/create_users.log
ss -lntu
echo "Mail server setup complete"