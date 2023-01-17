package app.channels.channeltypes;

import lombok.Getter;

@Getter
public class ChannelUpdate {
    Long userId;
    Long channelId;
    Long lastMessageId;
}
