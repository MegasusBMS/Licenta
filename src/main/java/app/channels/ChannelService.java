package app.channels;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    
    @Autowired
    ChannelRepository channelRepo;

    public void create(Channel channel) {
        channelRepo.save(channel);
    }

    public Channel getChannelByChannelName(String channelName) {
        List<Channel> channels = channelRepo.findByChannelName(channelName);
        return channels.size() > 0 ? channels.get(0) : new Channel();
    }
    

}
