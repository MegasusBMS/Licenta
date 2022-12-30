package app.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.channels.Channel;
import app.channels.ChannelService;
import app.channels.channeltypes.ChannelRecived;
import app.user.User;
import app.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONArray;

@Controller
@RequestMapping(path = "/mainpage")
public class MainPageControl{

    @Autowired
    UserService userService;
    @Autowired
    ChannelService channelService;

    @GetMapping
    public ModelAndView getTemplate(HttpServletRequest req, HttpServletResponse resp){
        
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

        mav.setViewName("mainpage");
        mav.addObject("userName", user.asUserToSend().getUserName());
        String strChannels=JSONArray.toJSONString(user.asUserToSend().getChannels());
        mav.addObject("channels",strChannels);
        return mav;
    }

    @PostMapping
    public ModelAndView getChannel(@ModelAttribute ChannelRecived channelRecived, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect: login");

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

        if(channelRecived == null)
        return mav;

        if(channelRecived.getChannelName()==null || channelRecived.getId()==null)
        return mav;

        if("".equals(channelRecived.getChannelName()) || 0==channelRecived.getId())
        return mav;

        Channel channel = channelService.getChannelByChannelName(channelRecived.getChannelName());

        if(!(channel.asChannelToSend().getId()==channelRecived.getId()))
        return mav;

        if(!channel.asChannelToSend().getUsers().stream().anyMatch(element -> element.getId()==user.asUserToSend().getId()))
            if(!(channel.asChannelToSend().getOwner().getId()==user.asUserToSend().getId()))
                return mav;

        mav.setViewName("redirect:/channel");
        mav.addObject("channel", channel.asChannelToSend().getChannelName());
        mav.addObject("channelId", channel.asChannelToSend().getId());
        return mav;
    }

}
