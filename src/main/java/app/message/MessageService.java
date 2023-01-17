package app.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.channels.Channel;
import app.channels.channeltypes.ChannelToSend;
import app.message.messagetypes.MessageToSend;

@Service
public class MessageService {
    
    MessageRepository messageRepo;

    private MessageService(MessageRepository messageRepo){
        this.messageRepo=messageRepo;
    }

    public List<MessageToSend> getMessagesFromChannel(ChannelToSend channel){
        List<MessageToSend> messages = new ArrayList<>();

        getAllMessages().forEach(msg -> {
            if(msg.asMessageToSend().getChannel().getId()==channel.getId())
            messages.add(msg.asMessageToSend());
        });

        return messages;
    }

    public List<MessageToSend> getMessagesFromChannel(Channel channel){
        List<MessageToSend> messages = new ArrayList<>();

        getAllMessages().forEach(msg -> {
            if(msg.asMessageToSend().getChannel().getId()==channel.getId())
            messages.add(msg.asMessageToSend());
        });

        return messages;
    }

    public void createMessage(Message msg){
        messageRepo.save(msg);
    }

    private List<Message> getAllMessages(){
        return messageRepo.findAll();
    }
}
