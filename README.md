# testing-framework

## Components
### Configuration
#### Tests configuration
```json
{
  "name": "Name of suite",
  "params": {
    "apiUrl": "http://localhost:8085/insight"
  },
  "tests": [
    {
      "name": "Get Insight",
      "tag": "get",
      "rest": {
        "request": {
          "method": "GET",
          "url": ":apiUrl/1",
          "headers": {
            "Accept": "application/json"
          }
        },
        "expected": {
          "status": 200,
          "body": {
            "id": 1,
            "message": "First stupid insight",
            "author": "gorskip"
          },
          "headers": {
            "Content-Type": "application/json;charset=UTF-8"
          }
        }
      }
    },
    {
      "name": "Post Insight",
      "tag": "post",
      "rest": {
        "request": {
          "method": "POST",
          "url": ":apiUrl",
          "body": {
            "message": "RestDB Message",
            "tags": [
              "development"
            ]
          },
          "headers": {
            "Content-Type": "application/json;charset=UTF-8"
          }
        },
        "expected": {
          "status": 200,
          "body": {
            "author": "gorskip",
            "message": "RestDB Message",
            "tags": [
              {
                "name": "development"
              }
            ]
          },
          "headers": {
            "Content-Type": "application/json;charset=UTF-8"
          }
        }
      }
    }
  ]
}
```
### Rest Client
#### Request execution
```java
RestClient restClient = new RestClientBuilder().build();
Response response = restClient.execute(request);
Expected expected = restData.getExpected();

verifyIf.given(response)
        .has(expected).status()
        .contains(expected).body();
```
#### Response parsing
