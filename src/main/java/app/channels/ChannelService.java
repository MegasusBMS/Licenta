package app.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    
    @Autowired
    ChannelRepository channelRepo;

    public void create(Channel channel) {
        channelRepo.save(channel);
    }

    public Channel getChannelById(Long id) {
        return channelRepo.findById(id).get();
    }
    

}
