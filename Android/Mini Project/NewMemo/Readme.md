## 2017.05.29.(월)

### NewMemo
---
#### 주요 학습 내용
[1] MVP 패턴 적용
[2]
[3]
[4]
[5] FloatingActionButton 키보드 창 위에 나올 수 있게 하는 코드

-
-
-
-
-
---
##### [1] MVP 패턴 적용
* ListActivity와 DetailActivity를 Presenter와 View의 분리하였음.
  - Presenter - 로직
  - View - 화면 구현
-
```java

```
- 2.
```java

```
- 3.
```java

```
- 4.
```java

```
---
##### [2] 타이틀2
- 1.
```java

```
- 2.
```java

```
- 3.
```java

```
- 4.
```java

```
---

##### [3] 타이틀3
- 1.
```java

```
- 2.
```java

```
- 3.
```java

```
- 4.
```java

```
---

##### [4] 타이틀4
- 1.
```java

```
- 2.
```java

```
- 3.
```java

```
- 4.
```java

```
---

##### [5] FloatingActionButton 키보드 창 위에 나올 수 있게 하는 코드
- [AndroidManifests.xml] 소스코드에서
  android:windowSoftInputMode="adjustResize"   추가
```xml
  <activity
      android:name=".DetailActivity"
      android:label="@string/title_activity_detail"
      android:theme="@style/AppTheme.NoActionBar"
      android:windowSoftInputMode="adjustResize">    // 추가한 부분
  </activity>
```
---
