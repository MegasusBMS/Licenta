package app.channels;

import java.util.HashSet;
import java.util.Set;

import app.channels.channeltypes.ChannelCreated;
import app.channels.channeltypes.ChannelToSend;
import app.message.Message;
import app.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Entity
@Table
@NoArgsConstructor
@ToString
public class Channel {
    @Id
    @SequenceGenerator(
        name = "channel_sequence",
        sequenceName = "channel_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "channel_sequence"
    )
    private Long id;
    private String channelName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "channel",fetch = FetchType.LAZY)
    private Set<Message> messages;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = {
        CascadeType.PERSIST
    })
    @JoinTable(name = "user_channel",
    joinColumns ={
        @JoinColumn(name="channel_id",referencedColumnName = "id")
    },
    inverseJoinColumns ={
        @JoinColumn(name="user_id",referencedColumnName = "id")
    } )
    private Set<User> users;

    public Channel(ChannelCreated channel,User user){
        this.channelName=channel.getChannelName();
        this.owner = user;
        this.users = new HashSet<User>();
        this.users.add(user);
    }

    public boolean addUser(User user){
        return users.add(user);
    }

    public boolean removeUser(User user){
        return users.remove(user);
    }

    public ChannelToSend asChannelToSend(){
        return new ChannelToSend(id, channelName, owner, users, messages);
    }

    public Channel removeJoints(){
        owner = new User();
        messages = new HashSet<>();
        users = new HashSet<>();
        return this;
    }

}
