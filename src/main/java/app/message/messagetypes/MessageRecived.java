package app.message.messagetypes;

import lombok.Getter;

@Getter
public class MessageRecived {
    String message;
    String channelName;
    Long channelId;
}
