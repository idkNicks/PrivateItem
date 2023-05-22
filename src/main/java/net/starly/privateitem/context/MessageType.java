package net.starly.privateitem.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {

    ERROR("errorMessages"), NORMAL("messages");

    public final String key;
}
