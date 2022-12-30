package app.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import app.channels.Channel;
import app.message.Message;
import app.user.usertypes.UserLogIn;
import app.user.usertypes.UserSignIn;
import app.user.usertypes.UserToSend;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    @Column(unique=true)
    private String userName;
    @Getter(value = AccessLevel.PRIVATE)
    private String password;

    @ManyToMany(cascade = {
        CascadeType.PERSIST
    },fetch = FetchType.LAZY,mappedBy = "users")
    private Set<Channel> channels;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner",fetch = FetchType.LAZY)
    private Set<Channel> ownedChannels;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sender",fetch = FetchType.LAZY)
    private Set<Message> messages;

    public User(User user){
        id = user.id;
        userName = user.userName;
        password = user.password;
        channels = user.channels;
        ownedChannels = user.ownedChannels;
        messages = user.messages;
    }

    public User(UserSignIn user){
        userName = user.getUserName();
        password = user.getPassword();
    }

    public User(UserLogIn user){
        userName = user.getUserName();
        password = user.getPassword();
    }

    public UserToSend asUserToSend(){
        return new UserToSend(id, userName, channels, ownedChannels, messages);
    }

    public User removeJoints(){
        messages = new HashSet<>();
        channels = new HashSet<>();
        ownedChannels = new HashSet<>();
        return this;
    }

    public boolean passwordEquals(String password){
        if(this.password == null || password == null)
        return false;
       return this.password.equals(password);
    }

}
