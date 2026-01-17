# PRI Deployment Guide
====================

## DEPLOYMENT REPO (REQUIRED)
--------------------------
https://github.com/System-PRI/deploy  
**This repo is main PRI system - clone & start it properly FIRST**

### 1. DATABASE SETUP
-----------------
**Check if user_data, students, supervisors, projects, roles contains usable data - this project creates own tables from those data** 

### 2. LDAP CERTIFICATE (REQUIRED for LDAP authentication)  
----------------------------------------------------
**Download DC2 cert**  
`openssl s_client -connect <<server dns address>> \
  -showcerts </dev/null 2>/dev/null | openssl x509 -outform PEM > dc-cert.pem`

**Import to truststore**  
`keytool -importcert -alias dc2-2016-ldaps -file dc-cert.pem \
  -keystore ldap-truststore.jks -storepass changeit -noprompt`

**Copy to deploy dir**  
`cp ldap-truststore.jks deploy/`

### 3. ENVIRONMENT CONFIG
--------------------
**Edit config.env (database, ports, credentials) as described in hints**  
`cd deploy/
cp config.env.example config.env`

### 4. START STACK
--------------
`docker-compose up -d`  
**Pulls latest images + starts all containers)**

### 5. AUTO UPDATES & MONITORING
----------------------------
**Watchtower: Daily 04:00 image update check + restart**  
**Healthcheck: Monitors app responsiveness**  

### 6. LOGS
-------
`docker logs prib --timestamps --since 1h`  
`docker logs prif --timestamps --since 1h`  
**logs from backend i frontend from 1h**
