package app.user.usertypes;

import java.util.List;
import java.util.Set;


import app.channels.Channel;
import app.channels.channeltypes.ChannelToSend;
import app.message.Message;
import app.message.messagetypes.MessageToSend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserToSend {

    private Long id;
    private String userName;
    private Set<Channel> channels;
    private Set<Channel> ownedChannels;
    private Set<Message> messages;
    
    public List<ChannelToSend> getChannels(){
        return channels.stream()
        .map(Channel::removeJoints)
        .map(Channel::asChannelToSend).toList();
    }

    public List<ChannelToSend> getOwnedChannels(){
        return ownedChannels.stream()
        .map(Channel::removeJoints)
        .map(Channel::asChannelToSend).toList();
    }
    
    public List<MessageToSend> getMessages(){
        return messages.stream().map(Message::removeJoints)
        .map(Message::asMessageToSend)
        .toList();
    }

}
    
