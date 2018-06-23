# testing-framework

## Components
### Configuration
#### Tests configuration
```json
{
  "name": "Name of suite",
  "params": {
    "apiUrl": "http://localhost:8080/insight",
    "suiteParam": "suiteParamValue",
    "id": 2346
  },
  "tests": [
    {
      "name": "Get Insights",
      "tag": "get",
      "rest": {
        "request": {
          "method": "GET",
          "url": ":apiUrl",
          "headers": {
            "Accept": "application/json"
          }
        },
        "expected": {
          "status": 200,
          "body": {
            "expectedField": "expectedValue"
          },
          "headers": {
            "Accept": "application/json",
            "Content-type": "application/json"
          }
        }
      },
      "db": {
        "query": "select * from table"
      },
      "mapper": "mapperName"
    },
    {
      "name": "Name of test 2",
      "params": {
        "testParam": "testParam 2"
      },
      "tag": "complex",
      "rest": {
        "params": {
          "apiParam": "apiParam"
        },
        "request": {
          "method": "POST",
          "body": {
            "date": "2018-06-08",
            "name": "Name of something",
            "someList": [
              "a",
              "b",
              "c"
            ]
          },
          "url": ":api_url/portfolio:dbParam",
          "headers": {
            "Accept": "application/json",
            "ContentType": "application/json"
          }
        }
      },
      "db": {
        "params": {
          "dbParam": "dbParamValue"
        },
        "query": ":dbParam :apiParam :testParam :suiteParam"
      }
    }
  ]
}
```
##### Configuration reading
```java
testSuite.getName();
testSuite.getParams();
List<TestCase> testCases = testSuite.getTests();

testCases.forEach(testCase -> {
    testCase.getName();
    testCase.getParams();
    testCase.getMapper();
    testCase.getTag();
        Rest restData = testCase.getRest();
            restData.getParams();
                Request request = restData.getRequest();
                    request.getUrl();
                    request.getMethod();
                    request.getHeaders();
                    request.getBody();
                Expected expected = restData.getExpected();
                    expected.getStatus();
                    expected.getHeaders();
                    expected.getBody();
        Db dbData = testCase.getDb();
            dbData.getParams();
            dbData.getParams();
});
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
### Assertions
#### VerifyIf
```java
new VerifyIf()
    .given(response)
    .has(expected)
    .status()
    .and(expected)
    .body()
    .and()
    .contains(expected)
    .headers();
 ```
 or:
 ```java
 new VerifyIf()
    .given(response)
    .has(expected)
    .status()
    .body()
    .contains(expected)
    .headers();
  ```
#### VerifyIf builder
```java
VerifyIf verifyIf = new VerifyIf.VerifyBuilder().build();
```
##### With order checking
```java
VerifyIf verifyIf = new VerifyIf.VerifyBuilder()
    .withOrderChecking()
    .build();
```
#### Example
##### Expected response:
```json
{
  "status": 200,
  "headers": null,
  "body": {
    "id": 1,
    "name": "Mock response",
    "type": [
      {"array": [1, 2]},
      {"array": [3, 4]}
    ]
  }
}
```
##### Response:
```json
{
  "status": 201,
  "headers": null,
  "body": {
    "name": "Mock response 2",
    "type": [
      {"array": [1, 2]},
      {"array": [4, 3]}
    ],
    "addedField": "value"
  }
}
```
##### Tested by:
```java
VerifyIf verifyIf = new VerifyIf.VerifyBuilder()
        .withOrderChecking()
        .build();

verifyIf.given(response)
        .has(expected)
        .status()
        .and()
        .body();
```
##### Assertion result:
```json
java.lang.AssertionError: Body: Response and Expected are not equal:
{
  "changed" : [ {
    "path" : "/name",
    "value" : "Mock response 2",
    "expectedValue" : "Mock response"
  } ],
  "removed" : [ {
    "path" : "/id"
  } ],
  "added" : [ {
    "path" : "/addedField",
    "value" : "value"
  } ],
  "moved" : [ {
    "from" : "/type/1/array/0",
    "path" : "/type/1/array/1"
  } ]
}
```
