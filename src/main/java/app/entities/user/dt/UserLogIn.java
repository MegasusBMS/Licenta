package app.entities.user.dt;

import app.entities.user.UserInterface;

public record UserLogIn(String userName, String password) implements UserInterface{
    
}
