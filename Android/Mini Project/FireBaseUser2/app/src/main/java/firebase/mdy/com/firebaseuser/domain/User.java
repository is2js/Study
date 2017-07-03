package firebase.mdy.com.firebaseuser.domain;

/**
 * Created by MDY on 2017-07-03.
 */

public class User {
    // 멤버 변수
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
        this.password = password;
    }
}