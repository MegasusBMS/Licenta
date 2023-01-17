package app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.channels.Channel;
import app.channels.ChannelService;
import app.channels.channeltypes.ChannelRecived;
import app.message.Message;
import app.message.MessageService;
import app.message.messagetypes.MessageRecived;
import app.user.User;
import app.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONArray;

@Controller
@RequestMapping("channel")
public class ChannelControl {
    @Autowired
    UserService userService;
    @Autowired
    ChannelService channelService;
    @Autowired
    MessageService messageService;

    @GetMapping
    public ModelAndView getTemplate(@ModelAttribute ChannelRecived channelRecived, HttpServletRequest req){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:login");

        if(req.getCookies()==null)
        return mav;

        List<Cookie> cookies = List.of(req.getCookies());

        if(cookies.size()==0)
        return mav;
        
        if(!cookies.stream().anyMatch(cookie -> "userId".equals(cookie.getName())))
        return mav;

        String userIdStr = cookies.stream().filter(cookie -> "userId".equals(cookie.getName())).findFirst().get().getValue();

        if(userIdStr==null||"".equals(userIdStr))
        return mav;

        long userId = Long.parseLong(userIdStr);
        
        if(!userService.userExist(userId))
        return mav;

        User user = userService.getUserById(userId);

        mav.setViewName("redirect:mainpage");
        mav.addObject("userName", user.asUserToSend().getUserName());
        String strChannels=JSONArray.toJSONString(user.asUserToSend().getChannels());
        mav.addObject("channels",strChannels);

        if(user.asUserToSend().getId()==null)
        return mav;

        if(channelRecived.getChannelName()==null || channelRecived.getId()==null)
        return mav;


        if("".equals(channelRecived.getChannelName()) || 0==channelRecived.getId())
        return mav;


        Channel channel = channelService.getChannelById(channelRecived.getId());

        if(channel==null)
            return mav;


        if(!(channel.asChannelToSend().getId()==channelRecived.getId()))
        return mav;
        
        if(!(user.asUserToSend().getChannels().stream().anyMatch(element -> element.getId()==channel.getId())))
            
            if(!(user.asUserToSend().getOwnedChannels().stream().anyMatch(element -> element.getId()==channel.getId())))
                return mav;

        mav.clear();
        mav.setViewName("channel");
        mav.addObject("userName", user.asUserToSend().getUserName());
        mav.addObject("userId", user.asUserToSend().getId());
        mav.addObject("channelName", channel.asChannelToSend().getChannelName());
        mav.addObject("channelId", channel.asChannelToSend().getId());
        String strMessages=JSONArray.toJSONString(messageService.getMessagesFromChannel(channel.asChannelToSend()));
        mav.addObject("messages", strMessages);
        
        return mav;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody MessageRecived messageRecived, HttpServletRequest req, HttpServletResponse rep) throws IOException{
        if(req.getCookies()==null)
        rep.sendRedirect("login");

        List<Cookie> cookies = List.of(req.getCookies());

        if(cookies.size()==0)
        rep.sendRedirect("login");
        
        if(!cookies.stream().anyMatch(cookie -> "userId".equals(cookie.getName())))
        rep.sendRedirect("login");

        String userIdStr = cookies.stream().filter(cookie -> "userId".equals(cookie.getName())).findFirst().get().getValue();

        if(userIdStr==null||"".equals(userIdStr))
        rep.sendRedirect("login");

        long userId = Long.parseLong(userIdStr);
        
        if(!userService.userExist(userId))
        rep.sendRedirect("login");

        User user = userService.getUserById(userId);
        if(user.asUserToSend().getId()==null)
        rep.sendRedirect("login");

        if(messageRecived == null)
        rep.sendRedirect("login");

        if(messageRecived.getChannelName()==null || messageRecived.getChannelId()==null || messageRecived.getMessage()==null)
        rep.sendRedirect("login");

        System.out.println("e");

        if("".equals(messageRecived.getChannelName()) || 0==messageRecived.getChannelId())
        rep.sendRedirect("login");

        System.out.println("f");

        Channel channel = channelService.getChannelById(messageRecived.getChannelId());;

        if(!(channel.asChannelToSend().getId()==messageRecived.getChannelId()))
        rep.sendRedirect("login");

        System.out.println("g");

        if(!channel.asChannelToSend().getUsers().stream().anyMatch(element -> element.getId()==user.asUserToSend().getId()))
            if(!(channel.asChannelToSend().getOwner().getId()==user.asUserToSend().getId()))
                rep.sendRedirect("login");
        System.out.println("h");

        if((!"".equals(messageRecived.getMessage())) && messageRecived.getMessage().length()<=200)
        messageService.createMessage(new Message(messageRecived,channel,user));
    }

}
