### Inflation
---
##### 1. 인플레이션(Inflation)
- XML 레이아웃에 정의된 내용이 메모리에 로딩된 후 객체화되는 과정
- 인플레이션 과정을 이해하는 것은 실제 앱을 만드는 과정에서 매우 중요하다.
- 왜냐하면 XML 레이아웃 파일은 앱이 실행되는 시점에 로드되어 메모리에 객체화되기 떄문이다.
##### 2. LayoutInflater 클래스
- 부분 화면을 위한 XML 레이아웃을 메모리에 객체화 하려면 별도의 인플레이션 객체를 사용해야 한다.
- 안드로이드에서는 이를 위해 LayoutInflater라는 클래스를 제공한다.

##### 3. LayoutInflater객체의 inflate() 메소드
- [Reference]
  View inflate (int resource, ViewGroup root)
  - 이 메소드의 첫번째 파라미터로는 XML 레이아웃 리소스를 저장하며,
  - 두번째 파라미터로는 뷰들을 객체화하여 추가할 대상이 되는 부모 컨테이너를 지정한다.
- LayoutInflater 객체의 경우, 시스템 서비스로 제공되므로 getSystemService()메소드를 사용해 객체를 참조한다.
---
