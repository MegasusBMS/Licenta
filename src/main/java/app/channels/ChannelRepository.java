package app.channels;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel,Long>{

    List<Channel> findByChannelName(String channelName);
    
}
