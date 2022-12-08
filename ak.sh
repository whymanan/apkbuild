
mkdir -p /var/www/html/$DOMAIN/$API_KEY
cp -r /home/ubuntu/mainapk /var/www/html/$DOMAIN/$API_KEY
cd /var/www/html/$DOMAIN/$API_KEY


find /var/www/html/$DOMAIN/$API_KEY -type f -exec sed -i -e s/NMA001/$API_KEY/g {} \;
find /var/www/html/$DOMAIN/$API_KEY -type f -exec sed -i -e s/3BY1VNMA001fwpD/$CLIENT_ID/g {} \;
find /var/www/html/$DOMAIN/$API_KEY -type f -exec sed -i -e s/nxuuMLYGMb92VWI3R62ff9e7f3916a/$SECRET_KEY/g {} \;
find /var/www/html/$DOMAIN/$API_KEY -type f -exec sed -i -e s#https://nextmoney.co.in/nextmoney#$CLIENT_APK_LINK#g {} \;
find /var/www/html/$DOMAIN/$API_KEY -type f -exec sed -i -e s#https://vitefintech.com/nextpay#$CLIENT_APK_LINK#g {} \;
