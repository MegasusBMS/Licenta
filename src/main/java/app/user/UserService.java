package app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.user.usertypes.UserLogIn;
import app.user.usertypes.UserSignIn;
import app.user.usertypes.UserToSend;


@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public boolean userLogin(UserLogIn userLogIn) {
        User user = getUserByUserName(userLogIn.getUserName());
        return user.passwordEquals(userLogIn.getPassword());
    }

    public boolean SignIn(UserSignIn user) {
        if(userExist(user))
            return false;
        userRepo.save(new User(user));
        return true;
    }

    public List<UserToSend> getAllUsers() {
        return userRepo.findAll().stream().map(User::asUserToSend).toList();
    }

    public User getUserByUserName(String userName){
        List<User> users = userRepo.findByUserName(userName);
        return users.size() > 0 ? users.get(0) : new User();
    }

    public User getUserById(long userId) {
        return userRepo.findById(userId).get();
    }

    public boolean userExist(User user){

        List<User> users = userRepo.findByUserName(user.asUserToSend().getUserName());
        return users.size() > 0;
        
    }

    public boolean userExist(String userName){
 
        List<User> users = userRepo.findByUserName(userName);
        return users.size() > 0;
    }

    public boolean userExist(UserSignIn user){
        List<User> users = userRepo.findByUserName(user.getUserName());
        return users.size() > 0;
    }

    public boolean userExist(Long userId){
        return userRepo.findById(userId).isPresent();
    }

}
