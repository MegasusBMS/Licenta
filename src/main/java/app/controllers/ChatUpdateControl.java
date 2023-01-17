package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.channels.Channel;
import app.channels.ChannelService;
import app.channels.channeltypes.ChannelUpdate;
import app.message.MessageService;
import app.message.messagetypes.MessageToSend;
import app.user.User;
import app.user.UserService;

@Controller
@RequestMapping("channelUpdate")
public class ChatUpdateControl {
    
    @Autowired
    MessageService messageService;
    @Autowired
    ChannelService channelService;
    @Autowired
    UserService userService;

    @PostMapping
    @ResponseBody
    List<MessageToSend> update(@RequestBody ChannelUpdate channelUpdate){

        if(channelUpdate == null)
            return null;

        if(channelUpdate.getChannelId()==null || channelUpdate.getUserId()==null)
            return null;

        Channel channel = channelService.getChannelById(channelUpdate.getChannelId());
        User user = userService.getUserById(channelUpdate.getUserId());
        
        if(channel == null || user == null)
            return null;

        List<MessageToSend> messages = messageService.getMessagesFromChannel(channel);

        if(messages.get(messages.size()-1).getId()==channelUpdate.getLastMessageId())
            return null;

        List<MessageToSend> listMsg = new ArrayList<>();

        for(int i = messages.size()-1;i>=0;i--){

            if(messages.get(i).getId()==channelUpdate.getLastMessageId())
            break;
            
            if(messages.get(i).getSender().getId()==user.asUserToSend().getId())
            continue;

            listMsg.add(messages.get(i));
        }

        return listMsg;
    }
}
 