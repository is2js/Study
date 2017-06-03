# Camera
Gallery 와 Camera 기능을 사용해본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Camera/app/src/main/java/com/mdy/android/camera/MainActivity.java)**


## 권한체크
- 롤리팝 이상 버전에서는 **File Provider** 라는 것을 이용해 권한을 획득해야 한다.
  (File Provider와 Content Provider는 아무런 연관이 없다.)
> ### File Provier를 사용하는 이유
>> 내가 이미지 파일을 저장할 특정 저장 공간에 대한 권한을 획득하는 것이다.
>> 그 저장 공간은 외부 공간이어서 모든 앱들이 같이 액세스를 할 수 있다.
>> 그래서 거기서 실제 write를 하겠다고 권한을 얻는 것이다.
- **AndroidManifest.xml** 에 아래 코드를 추가
  (사진을 저장하기 위한 파일에 대한 권한을 획득하기 위한 설정)
```xml
<!-- 사진을 저장하기 위한 파일에 대한 권한을 획득하기 위한 설정 -->
  <provider
      android:name="android.support.v4.content.FileProvider"
      android:authorities="${applicationId}.provider"
      android:exported="false"
      android:grantUriPermissions="true">
      <!-- resource 파일을 res/xml 폴더 안에 생성 -->
      <meta-data
          android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/file_path"/>         <!-- res - xml - file_path.xml 을 만들어준다. -->
  </provider>
```

- res - xml - file_path.xml 을 만들어준다.
```xml
  <?xml version="1.0" encoding="utf-8"?>
  <paths>
      <!-- name = content:// 로 시작하는 uri 주소체계의 suffix 가 된다. 즉, uri의 suffix이다.  -->
      <!-- 안드로이드에서 내 자원을 엑세스 하기 위해서 가지고 있는 주소이다. -->

      <!-- path = /External Storage/CameraN 가 된다. 물리경로의 실제 디렉토리 이름이다. 안드로이드에서 내 자원을 엑세스하기 위해서 가지고 있는 주소이다. -->
      <!-- 파일탐색기 구조를 정의하기 위한 것이다. -->
      <external-path name="Camera" path="CameraN" />
  </paths>

```




- 특정 권한이 있는지 시스템에 물어본다.
```java
  // 마시멜로 이상 버전에서만 런타임 권한 체크
  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      // level 23은 마시멜로이다.  Build.VERSION_CODES.M : 마시멜로를 가리킨다.
      // 마시멜로부터는 앞에 대문자 한자만 써도 된다.
      checkPermission();
  } else {
      // 아니면 그냥 run() 메소드 실행
      init();
  }


  }

  @TargetApi(Build.VERSION_CODES.M)       // @RequireApi 와 @TargetApi 는 동일하다고 생각해도 된다.
  private void checkPermission() {
  // 1. 권한체크 - 특정권한이 있는지 시스템에 물어본다.
  // checkSelfPermission 반환값이 true, false가 아니라 미리 정의된 상수로 반환한다.
  if( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
          && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
      init();
  } else {
      // 2. 권한이 없으면 사용자에게 권한을 달라고 요청
      String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; // 동시에 여러개 호출할 수 있으니까 복수로
      requestPermissions(permissions , REQ_PERMISSION);  // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다.

  }
  }

  // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  if(requestCode == REQ_PERMISSION) {
      // 3.1 사용자가 승인을 했음.
      if(grantResults[0] == PackageManager.PERMISSION_GRANTED
              && grantResults[1] == PackageManager.PERMISSION_GRANTED){
          init();
      // 3.2 사용자가 거절 했음.
      } else {
          cancel();
      }
  }
  }
```

## Camera 기능 이용
- **롤리팝 버전까지** 는 권한 획득없이 사용이 가능하나
  **마시멜로 이상 버전** 은 파일 프로바이더를 통해 권한을 획득

```java
  Uri fileUri = null;
  private void takePhoto(){
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
          File photoFile = null;
          try {
              photoFile = createFile();   //파일을 만들어서 담는다.

              if(photoFile != null){
                  // 마시멜로 이상 버전은 파일 프로바이더를 통해 권한을 획득
                  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                      fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                  // 롤리팝 버전은 권한 없이 획득이 가능
                  } else {
                      fileUri = Uri.fromFile(photoFile);
                  }
                  intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                  startActivityForResult(intent, Const.Camera.REQ_CAMERA);
              }
          }catch(Exception e){
              //  e.printStackTrace();  -> 예외처리에서 에러가 나면 이렇게 해주면 에러를 확인할 수 있다.
              Toast.makeText(getBaseContext(), "사진파일 저장을 위한 임시파일을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
              return;  // 사진파일이 생성안되면 더이상 진행되지 않게 하려고 return; 한다.
          }

      } else { // 롤리팝 미만 버젼에서만 바로 실행
          startActivityForResult(intent, Const.Camera.REQ_CAMERA);
      }
  }

  private File createFile() throws IOException{
      // 임시파일명 생성
      String tempFileName = "TEMP_"+System.currentTimeMillis();
      // 임시파일 저장용 디렉토리 생성
      File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
      // Environment.getExternalStorageDirectory() : 루트경로를 가져옴.
      if(!tempDir.exists()){
          tempDir.mkdir();
      }
      // 실제 임시파일을 생성
      File tempFile = File.createTempFile(
              tempFileName,
              ".jpg",
              tempDir
      );
      return tempFile;
  }

  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          // 요청코드 구분
          if(requestCode == Const.Camera.REQ_CAMERA){
              // 결과처리 상태 구분
              if(resultCode == RESULT_OK) {
                  Uri imageUri = null;
                  // 롤리팝 미만 버전에서는 data 인텐트에 찍은 사진의 uri 가 담겨온다.
                  if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                      imageUri = data.getData();
                  } else {
                      imageUri = fileUri;
                  }
                  Log.i("Camera","file=============================="+fileUri);
                  Log.i("Camera","file=============================="+imageUri);
                  imageView.setImageURI(imageUri);
              }
          }
      }
```

## 소제목3
- 소제목3에 대한 설명
```java

```

## 소제목3
- 소제목3에 대한 설명
```java

```
