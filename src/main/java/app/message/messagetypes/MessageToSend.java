package app.message.messagetypes;

import app.channels.Channel;
import app.channels.channeltypes.ChannelToSend;
import app.user.User;
import app.user.usertypes.UserToSend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageToSend {
    private Long id;
    private User sender;
    private Channel channel;
    private String context;
    private Long senderId;

    public UserToSend getSender(){
        return sender.removeJoints().asUserToSend();
    }
    
    public ChannelToSend getChannel(){
        return channel.removeJoints().asChannelToSend();
    }
}
