package app.message;

import app.channels.Channel;
import app.message.messagetypes.MessageRecived;
import app.message.messagetypes.MessageToSend;
import app.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    @Id
    @SequenceGenerator(
        name = "message_sequence",
        sequenceName = "message_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "message_sequence"
    )
    private Long id;
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;

    public Message(MessageRecived message){
        
    }

    public MessageToSend asMessageToSend(){
        return new MessageToSend(id, sender, channel, context,sender.asUserToSend().getId());
    }

    public Message removeJoints(){
        sender = new User();
        channel = new Channel();
        return this;
    }
}
