package app.user.usertypes;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.Getter;

@Getter
public class UserLogIn {

    private String userName;
    private String password;

    public UserLogIn(String userName,String password){
        this.userName=userName;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

}
