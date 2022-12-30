package app.user.usertypes;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.Getter;

@Getter
public class UserSignIn {
    
    private String userName;
    private String password;

    public UserSignIn(String userName,String password){
        this.userName=userName;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

}
