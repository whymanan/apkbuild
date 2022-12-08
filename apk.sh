#!/bin/bash
export API_KEY="JSNA001" #NMA001 45/56 #POCA001
export CLIENT_ID="srUkJJSNA001T7c"  #3BY1VNMA001fwpD 1/1  #doj5SPOCA001lgu
export SECRET_KEY="lsOt7D0tUKexiXh9B6389cfee1e8e9" #nxuuMLYGMb92VWI3R62ff9e7f3916a 1/3   #XsE0Mh2RNkiSbanac632ef26e7c9d3

export CLIENT_APK_LINK="testing.vitefintech.co.in/testing" #https://nextmoney.co.in/nextmoney/ #36/46 #https://payonclick.co.in/payonclick
export FOLDER_NAME="testing" #payonclick
export DOMAIN="testing.vitefintech.co.in" #PAYONCLICK.CO.IN
export AUTH="testing-auth" #payonclick.co.in/payonclick-auth

export DBUSER="whymanan_user" #NEWDATABASEUSER 
export DBPASS="asjdbgoasjgbkasjgbjoasg" #databasepasswordhere
export DBNAME="whymanan_database" #NEWDATABASENAME
export DBHOST="148.66.132.29" #148.66.132.29

export ROLES="JSN" #POC

export FHOSTNAME="148.66.132.29"
export FUSER="manan@testing.vitefintech.co.in"
export FPASS="testing.vitefintech.co.in"
export REMOTE="/"




mkdir -p /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY
cp -r /home/ubuntu/mainapk /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY
cd /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY


find /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY -type f -exec sed -i -e s/NMA001/$API_KEY/g {} \;
find /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY -type f -exec sed -i -e s/3BY1VNMA001fwpD/$CLIENT_ID/g {} \;
find /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY -type f -exec sed -i -e s/nxuuMLYGMb92VWI3R62ff9e7f3916a/$SECRET_KEY/g {} \;
find /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY -type f -exec sed -i -e s#https://nextmoney.co.in/nextmoney#$CLIENT_APK_LINK#g {} \;
find /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY -type f -exec sed -i -e s#https://vitefintech.com/nextpay#$CLIENT_APK_LINK#g {} \;

cd /home/ubuntu/mainapk/output/$DOMAIN/$API_KEY
tar -zcvf $API_KEY.tar.gz mainapk
rm -r mainapk
