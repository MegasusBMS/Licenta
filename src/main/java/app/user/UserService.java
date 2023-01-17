package app.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import app.user.usertypes.UserLogIn;
import app.user.usertypes.UserSignIn;
import app.user.usertypes.UserToSend;


@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public User userLogin(UserLogIn user) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnorePaths("id");
        Example<User> example = Example.of(new User(user),matcher);
        Optional<User> optUser= userRepo.findOne(example);
        return optUser.isPresent() ? optUser.get() : new User();
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

    public User getUserById(long userId) {
        return userRepo.findById(userId).get();
    }

    public boolean userExist(User user){

        return userRepo.findById(user.asUserToSend().getId()).isPresent();
        
    }

    public boolean userExist(UserLogIn user){
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnorePaths("id", "password");
        Example<User> example = Example.of(new User(user),matcher);
    
        return userRepo.findOne(example).isPresent();
    }

    public boolean userExist(UserSignIn user){
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnorePaths("id", "password");
        Example<User> example = Example.of(new User(user),matcher);
    
        return userRepo.findOne(example).isPresent();
    }

    public boolean userExist(Long userId){
        return userRepo.findById(userId).isPresent();
    }

}
