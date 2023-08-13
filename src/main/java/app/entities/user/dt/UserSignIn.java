package app.entities.user.dt;

import app.entities.user.UserInterface;

public record UserSignIn(String userName, String password,String cnp) implements UserInterface{
}
