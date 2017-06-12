# HttpUrlConnection
#### 1. JSON
#### 2. REST API
#### 3. HttpUrlConnection

- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpUrlConnection/app/src/main/java/com/mdy/android/httpurlconnection/MainActivity.java)**

## 1. JSON
#### (1) json 기본형
- 문법
  - json 오브젝트 = { 중괄호와 중괄호 사이 }
- JSON 데이터는
```
{ "변수명" : "값", "변수명2" : "값2", "변수명3" : "값3" }
```

#### (2) json 서브트리
- 문법
```
{ "변수명" : json 오브젝트 }
{ "변수명" : { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" } }
```

- 사용방법
```
json.변수명.서브명    //  <-  값이 꺼내진다.
```


#### (3)) json 배열
- 문법
```
{ "변수명" : json 배열 }
{ "변수명" : [
               { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             , { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             , { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             ]
}
```

- 사용방법
```
json.변수명[0].서브명.
json.변수명[1].서브명2
```



## 2. REST API
- 서울 열린데이터 광장
  - http://data.seoul.go.kr/
- 서울시 공중화장실 위치정보
  - http://data.seoul.go.kr/openinf/sheetview.jsp?infId=OA-162&tMenu=11
- Open API
  - 샘플URL (서울시화장실정보)
    - http://openAPI.seoul.go.kr:8088/(인증키)/xml/SearchPublicToiletPOIService/1/5/
    - [Xml] http://openAPI.seoul.go.kr:8088/sample/xml/SearchPublicToiletPOIService/1/5/
    - [JSON] http://openAPI.seoul.go.kr:8088/sample/json/SearchPublicToiletPOIService/1/5/

#### REST API 주소체계 와 Query String 비교
- REST API 주소체계
```
  openApi.seoul.go.kr
  /sample
  /json
  /GeoINfoPoolWGS
  /1
  /5
```

- Query String
```
  openApi.seoul.go.kr
    ? key     = sample
    & type    = json
    & service = GeoINfoPoolWGS
    & start   = 1
    & end     = 5
```

- 서버에서는 REST API 형태와 비 REST API 형태로 들어오는 요청에 대해 각각 응답을 해주도록 설계되어 있다.
- 기존에는 xml로 데이터를 주고 받았었는데 더 간편하고, 데이터의 크기를 줄이기 위해 JSON이 개발되어 사용되고 있다.
