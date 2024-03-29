# money-transfer-rest-api


[![Build Status](https://travis-ci.org/shafikul/money-transfer-rest-api.svg?branch=master)](https://travis-ci.org/shafikul/money-transfer-rest-api)

## HOW TO RUN 

At first clone this repository https://github.com/shafikul/money-transfer-rest-api.git 

Then browse to root folder and run ***mvn clean package***  

money-transfer-rest-api-1.0-jar-with-dependencies.jar  file will be created in target folder  

Run the jar ***java -jar target/money-transfer-rest-api-1.0-jar-with-dependencies.jar***  

Application will be started in ***http://localhost:8888***  


## API DOCUMENTATION

### 1. Get Account list  

#####  URL: http://localhost:8888/api/v1/account/all?limit=1

#####  Method: GET  

##### Success Response:

 ```json
  {
  "type": "SUCCESS",
  "status": 200,
  "message": "SUCCESS",
  "result": [
    {
      "id": 1,
      "name": "sagar",
      "balance": 11.0
    }
  ]
}
```

##### Error Response:

```json
  {
    "type": "ERROR",
    "status": 404,
    "message": "Not Found",
    "result": null,
    "error": null
  }
```

### 2. Get account details with recent transactions by accountId & transaction limit

#####  URL: http://localhost:8888/api/v1/account?id=5&limit=2

#####  Method: GET  

##### Success Response:

 ```json
  {
  "type": "SUCCESS",
  "status": 200,
  "message": "OK",
  "result": {
    "id": 1,
    "name": "sagar",
    "balance": 11.0,
    "transactions": [
      {
        "fromAccountId": 2,
        "toAccountId": 1,
        "amount": 1,
        "timestamp": 1563740260141,
        "state": "COMPLETE",
        "type": "TRANSFER"
      },
      {
        "fromAccountId": 1,
        "toAccountId": 1,
        "amount": 10.0,
        "timestamp": 1563740056162,
        "state": "COMPLETE",
        "type": "DEPOSIT"
      }
    ]
  }
}
```

##### Error Response:

```json
  {
    "type": "ERROR",
    "status": 404,
    "message": "Not Found",
    "result": null,
    "error": null
  }
```

### 3. Open user account with primary balance(0.0 acceptable)

#####  URL: http://localhost:8888/api/v1/account/create

#####  Method: POST
##### CURL:

```curl
  curl -i -X POST -H 'Content-Type: application/json' -d '{"name": "sagar", "primaryBalance": 10.0, "birth": "1992-01-14"}' http://localhost:8888/api/v1/account/create
```


##### Request Body:

```json
  {"name": "sagar", "primaryBalance": 10.0, "birth": "1992-01-14"}
```

##### Success Response:

 ```json
  {
  "type": "SUCCESS",
  "status": 201,
  "message": "CREATED",
  "result": {
    "id": 1,
    "name": "sagar",
    "balance": 10.0
  }
}
```

##### Error Response:

```json
{
    "type": "ERROR",
    "status": 400,
    "message": "Invalid Payload"
}
 
```

### 4. Transfer money from one account to another 

#####  URL: http://localhost:8888/api/v1/account/transfer
 
#####  Method: POST  

##### CURL:

```curl
 curl -i -X POST -H 'Content-Type: application/json'  -d '{"toAccount": 1, "fromAccount": 2, "money": 1}' http://localhost:8888/api/v1/account/transfer   

```
##### Request Body:

```json
  {"toAccount": 1, "fromAccount": 2, "money": 1}
```
##### Note: For transfer toAccount and fromAccount must be different

##### Success Response:

 ```json
  {
  "type": "SUCCESS",
  "status": 201,
  "message": "COMPLETE",
  "result": {
    "fromAccountId": 2,
    "toAccountId": 1,
    "amount": 1,
    "timestamp": 1563740260141,
    "state": "COMPLETE",
    "type": "TRANSFER"
  }
}
```

##### Error Response:

```json
 {
  "type": "ERROR",
  "status": 404,
  "message": "NOT FOUND"
}
```

### 5. Deposit money to account   

#####  URL: http://localhost:8888/api/v1/account/send

#####  Method: POST 

##### CURL: 
```curl
  curl -i -X POST -H 'Content-Type: application/json'  -d '{"toAccount": 1, "fromAccount": 1, "money": 10.12} http://localhost:8888/api/v1/account/send   
``` 

##### Request Body:

```json
  {"toAccount": 1, "fromAccount": 1, "money": 10.12}
```
##### Note: For deposit toAccount and fromAccount must be same

##### Success Response:

 ```json
  {
  "type": "SUCCESS",
  "status": 201,
  "message": "COMPLETE",
  "result": {
    "fromAccountId": 1,
    "toAccountId": 1,
    "amount": 10.12,
    "timestamp": 1563741274394,
    "state": "COMPLETE",
    "type": "DEPOSIT"
  }
}
```

##### Error Response:

```json
  {
    "type": "ERROR",
    "status": 400,
    "message": "Bad Request",
    "result": null
}
```
##### Record Not Found Error
```json
  {
    "type": "ERROR",
    "status": 404,
    "message": "Not Found",
    "result": null,
    "error": null
  }
```

### 6. Withdraw money from account

#####  URL: http://localhost:8888/api/v1/account/with-draw 

#####  Method: POST 

##### CURL:

```curl
  curl -i -X POST -H 'Content-Type: application/json'  -d '{"toAccount": 1, "fromAccount": 1, "money": 10.12}' http://localhost:8888/api/v1/account/with-draw   
```

##### Request Body:

```json
  {"toAccount": 1, "fromAccount": 1, "money": 10.12}
``` 
##### Note: For withdraw toAccount and fromAccount must be same

##### Success Response:

 ```json
 {
  "type": "SUCCESS",
  "status": 201,
  "message": "COMPLETE",
  "result": {
    "fromAccountId": 1,
    "toAccountId": 1,
    "amount": 0.12,
    "timestamp": 1563741039000,
    "state": "COMPLETE",
    "type": "WITH_DRAW"
  }
}
```

##### Error Response:

```json
  {
  "type": "ERROR",
  "status": 403,
  "message": "INSUFFICIENT_BALANCE",
  "result": {
    "fromAccountId": 1,
    "toAccountId": 1,
    "amount": 10.12,
    "timestamp": 1563741158867,
    "state": "INSUFFICIENT_BALANCE",
    "type": "WITH_DRAW"
  }
}
```
### 7. Get all transactions

#####  URL: http://localhost:8888/api/v1/transactions/all?limit=3 

##### CURL: curl -i -X GET http://localhost:8888/api/v1/transactions/all  

#####  Method: GET 

##### Success Response:

 ```json
 {
  "type": "SUCCESS",
  "status": 200,
  "message": "SUCCESS",
  "result": [
    {
      "fromAccountId": 1,
      "toAccountId": 1,
      "amount": 10.0,
      "timestamp": 1563740056162,
      "state": "COMPLETE",
      "type": "DEPOSIT"
    },
    {
      "fromAccountId": 2,
      "toAccountId": 2,
      "amount": 10.0,
      "timestamp": 1563740252785,
      "state": "COMPLETE",
      "type": "DEPOSIT"
    },
    {
      "fromAccountId": 2,
      "toAccountId": 1,
      "amount": 1,
      "timestamp": 1563740260141,
      "state": "COMPLETE",
      "type": "TRANSFER"
    }
  ]
}
```

##### Empty Response:

```json
  {
  "type": "SUCCESS",
  "status": 200,
  "message": "SUCCESS",
  "result": []
}
```
