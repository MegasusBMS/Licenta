package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.channels.Channel;
import app.channels.ChannelService;
import app.channels.channeltypes.ChannelCreated;
import app.user.User;
import app.user.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("newChannel")
public class NewChannelControl {

    @Autowired
    UserService userService;
    @Autowired
    ChannelService channelService;

    @GetMapping
    public void getTemplate(){}

    @PostMapping
    public ModelAndView newChannel(@ModelAttribute ChannelCreated channelCreated, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:login");
        if(req.getCookies()==null)
        return mav;
        
        System.out.print("ChannelName: " + channelCreated.getChannelName());

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

        if(user.asUserToSend().getId()==null)
        return mav;

        Channel channel = new Channel(channelCreated, user);
        channelService.create(channel);

        mav.setViewName("redirect:mainpage");

        return mav;
    }
}
