package app.message;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    MessageRepository messageRepo;

    private MessageService(MessageRepository messageRepo){
        this.messageRepo=messageRepo;
    }

}
