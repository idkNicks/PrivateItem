package net.starly.privateitem.context;

import lombok.Getter;

public enum MessageType {

    ERROR("errorMessages"), NORMAL("messages");

    @Getter public final String key;

    MessageType(String key) {
        this.key = key;
    }
}
