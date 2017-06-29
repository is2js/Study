# MongoDB
#### MongoDB에 대해서 알아본다.

<br>

## MongoDB 실행
#### 1. MongoDB DB 연결
- MongoDB설치 폴더로 이동
  - cd C:\Program Files\MongoDB\Server\3.4\bin
- MongoDB설치 폴더 - bin 폴더 안의 콘솔 창에서
  - mongod--dbpath 데이터베이스경로
  - 예시) mongod --dbpath c:/workspaces/MongoDB/databases

#### 2. MongoDB Client 실행
- MongoDB설치 폴더 - bin 폴더 안의 콘솔 창에서
  - mongo  입력

<br>

## MongoDB 기본 명령어 모음
- #### mongoDB 버전확인
```
  > mongod --version;
```

- #### 현재 사용중인 데이터베이스를 확인
```
  > db;
```

- #### 내가 만든 데이터베이스 리스트들을 확인
```
  > show dbs;
```

- #### 사용할 데이터베이스를 지정
```
  > use bbs;
```
- #### 내가 만든 데이터베이스의 collection(table) 리스트를 확인
```
  > show collections;
   또는
  > show tables;
```
- #### 데이터베이스의 collection(table)을 생성
```
  > db.createCollection("테이블명");
```
> ex) db.createCollection("board");

- #### 데이터베이스의 collection(table) 안의 내용을 조회
```
  > db.테이블명.find();
```
>

<br>

## CRUD 명령어

- #### 입력 (Create)
```
  > db.테이블명.insert( {"이름" : "값"} )
```

- #### 읽기 (Read)
> 모든 문서 반환
```
  > db.테이블명.find();

  ex) db.board.find();
```
> 한 문서 반환
```
  > db.테이블명.findOne()

  ex) db.board.findOne( {"key1" : "value1"} )
```

- #### 수정 (Update)
```
  > db.테이블명.update( { 키 : '값' }, { $set: { 수정할항목 : '수정할값' } })

  ex) db.board.update( { id : 1 }, {$set : { title : '제목1'} })
     -> id가 1인 자료의 title을 제목1 로 수정하라는 의미
```

- #### 삭제 (Delete)
```
  > db.테이블명.remove( {"key1" : "value1"} )

  ex) db.board.remove({_id: ObjectId('38cade47c79dbacd2c3b1a81')})
```

- #### collection(테이블) 삭제
```
  > db.[테이블명].drop()
```
