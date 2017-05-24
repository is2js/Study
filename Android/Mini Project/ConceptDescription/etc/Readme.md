## 2017.05.24.(수) - 수업내용

#### <이미지 파일 사용>
 - 그림파일 5개 다운, 확장자 같게 만들어서 사용한다. (jpg)
   - xxhdpi(full hdpi) 대부분 여기에 넣어서 한다.
   - JPG와 JPEG는 다른 것이다.
---
#### <LinearLayout 사이즈 조절>
 - Linear – layout_width를 0dp로 해놓고
  각 textView의 layout_weigth를 각각 1, 9로 주면 1:9비율로 배치된다.
- LinearLayout의 layout_height를 50dp로 주었는데 바뀌지 않으면
  LinearLayout의 layout_width를 match_parent로 해놓고,
  TextView의 layout_height를 50dp를 주면 된다.
---
#### <리소스 아이디 가져오기>
##### 1. mipmap폴더
```java
int image_suffix = (data.getId() % 5) + 1;
int id = context.getResources().getIdentifier("baby" + image_suffix, "mipmap", context.getPackageName());
```
##### 2. value폴더 - strings.xml
**<strings.xml>에서 가져올 경우.**
```java
for(int i=0; i < 10 ; i ++) {
  int stringValue = context.getResources().getIdentifier("te" + i, "string", context.getPackageName());
}
```
##### 3. id값
```java
for(int i=0; i < 10 ; i ++) {
  int id = getResources().getIdentifier("btn" + i, "id", getPackageName());
  findViewById(id).setOnClickListener(this);
}
```
 * xml파일의 @id   or  @mipmap   or @string  의 값이 **getIdentifier() 메소드** 의 두 번째 인자값에 들어간다.
 ---
 #### <scaleType 8가지 종류>
 Scaltype에는 8가지 종류가 있습니다.  

 1. matrix(원본 그대로)
 2. center(가운데 정렬)
 3. **centerCrop(비율을 유지하며 가운데를 중심으로 자른다.)**
 4. centerInside(비율을 유지하며 줄어듬)
 5. fitStart(왼쪽 위 정렬/ 비율유지)
 6. fitCenter(centerInside와 동일)
 7. fitEnd(왼쪽 아래 정렬 / 비율유지)
 8. fitXY(늘이기 ImageView를 비율에 상관없이 다 채웁니다.)


 ---





 
