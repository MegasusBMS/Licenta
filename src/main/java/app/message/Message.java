package app.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private String rawMessage;
    private Date dateOfCreate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;

    public Message(MessageRecived message,Channel channel,User sender){
        this.rawMessage=message.getMessage();
        this.channel=channel;
        this.sender=sender;
        this.dateOfCreate=Calendar.getInstance().getTime(); 
    }

    public MessageToSend asMessageToSend(){
        return new MessageToSend(id, sender, stringDateFormat(dateOfCreate), channel, rawMessage, sender.asUserToSend().getId());
    }

    public Message removeJoints(){
        sender = new User();
        channel = new Channel();
        return this;
    }

    private String stringDateFormat(Date date){
        Date thisDay = Calendar.getInstance().getTime(); 
        if(date==null){
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            return dateFormat.format(thisDay); 
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  
        String strDate = dateFormat.format(date);  
        String strThisDay = dateFormat.format(thisDay);

        if(!strThisDay.equals(strDate))
            dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm"); 
        else
            dateFormat = new SimpleDateFormat("hh:mm"); 
        
        return dateFormat.format(date);
    }
}
