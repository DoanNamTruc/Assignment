# Assignment
User and promotion service

SETUP DB, redis, user service, promotion service
-> docker-compose up -d

API
create new promotion:
promotion type FIRST_LOGIN and disccount 30%
->CURL : 
curl --location 'http://localhost:8081/api/v1/promotion/create' \
--header 'Content-Type: application/json' \
--data '{
    "name": "name",
    "code": "FIRST_LOGIN",
    "status": "RUN",
    "content": "{\n  \"limit\": 100,\n  \"discountPercent\": 30 \n}"
}'


REGISTER NEW USER:
curl --location 'http://localhost:8080/api/v1/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"c2ak1e1112@gmail.com",
    "phoneNumber":"02622111200992",
    "username":"123213113",
    "password":"bbbb"
}'

LOGIN:
curl --location 'http://localhost:8080/api/v1/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"c2ak1e1112@gmail.com",
    "phoneNumber":"02622111200992",
    "username":"123213113",
    "password":"bbbb"
}'