package app.message.messagetypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageRecived {
    String message;
    String channelName;
    Long channelId;
}
