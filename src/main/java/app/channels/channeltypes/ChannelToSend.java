package app.channels.channeltypes;

import java.util.List;
import java.util.Set;

import app.message.Message;
import app.message.messagetypes.MessageToSend;
import app.user.User;
import app.user.usertypes.UserToSend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChannelToSend {

    private Long id;
    private String channelName;
    private User owner;
    private Set<User> users;
    private Set<Message> messages;

    public UserToSend getOwner(){
        return owner.removeJoints().asUserToSend();
    }
    
    public List<UserToSend> getUsers(){
        return users.stream()
        .map(User::removeJoints)
        .map(User::asUserToSend).toList();
    }

    public List<MessageToSend> getMessages(){
        return messages.stream().map(Message::removeJoints)
        .map(Message::asMessageToSend)
        .toList();
    }

    

}
