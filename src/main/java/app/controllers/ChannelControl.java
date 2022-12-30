package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.channels.Channel;
import app.channels.ChannelService;
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
    // https://www.baeldung.com/spring-thymeleaf-path-variables
    @Autowired
    UserService userService;
    @Autowired
    ChannelService channelService;
    @Autowired
    MessageService messageService;

    @GetMapping
    public ModelAndView getTemplate(@ModelAttribute MessageRecived message, HttpServletRequest req, HttpServletResponse resp){
        
        System.out.println("ok");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/login");

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

        mav.setViewName("redirect:/mainpage");
        mav.addObject("userName", user.asUserToSend().getUserName());
        String strChannels=JSONArray.toJSONString(user.asUserToSend().getChannels());
        mav.addObject("channels",strChannels);

        if(message == null)
        return mav;

        if(message.getChannelName()==null || message.getChannelId()==null)
        return mav;

        if("".equals(message.getChannelName()) || 0==message.getChannelId())
        return mav;

        Channel channel = channelService.getChannelByChannelName(message.getChannelName());

        if(!(channel.asChannelToSend().getId()==message.getChannelId()))
        return mav;

        if(!channel.asChannelToSend().getUsers().stream().anyMatch(element -> element.getId()==user.asUserToSend().getId()))
            if(!(channel.asChannelToSend().getOwner().getId()==user.asUserToSend().getId()))
                return mav;
            
        mav.clear();
        mav.addObject("userName", user.asUserToSend().getUserName());
        mav.addObject("channelName", channel.asChannelToSend().getChannelName());
        mav.addObject("channelId", channel.asChannelToSend().getId());
        String strMessages=JSONArray.toJSONString(user.asUserToSend().getChannels());
        mav.addObject("messages", strMessages);
        
        return mav;
    }

}
