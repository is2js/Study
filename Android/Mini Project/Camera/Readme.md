# Camera
Gallery 와 Camera 기능을 사용해본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/WebView/app/src/main/java/com/mdy/android/camera/MainActivity.java)**

## 권한체크
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
