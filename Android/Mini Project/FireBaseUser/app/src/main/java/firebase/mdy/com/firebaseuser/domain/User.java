package firebase.mdy.com.firebaseuser.domain;

/**
 * Created by MDY on 2017-07-03.
 */

public class User {
    // 멤버필드, 속성, 멤버변수, 전역변수 -> 4개 다 같은말
    public String username;
    public String email;
    public String password;

    // 생성자
    public User(){

    }

    // 파라미터가 있는 생성자 오버로드
    public User(String username, String email, String password){
        this.username = username;
        this.email = email;

        // password를 단방향 암호화
        this.password = password;
    }
}
