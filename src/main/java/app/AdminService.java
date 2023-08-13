package app;

import org.springframework.stereotype.Service;

import app.entities.user.User;
import app.entities.user.UserService;

@Service
public class AdminService {
    
    private UserService userService;

    private AdminService(UserService userService){
        this.userService=userService;
        createAdminIfNotExist();
    }

    public void createAdminIfNotExist(){
        User admin = new User("admin","admin");
        if(userService.getUserByUserName(admin).isPresent())
            return;
        userService.createUser(admin);
    }

}
