package com.bdool.chatservice.util;

import java.util.UUID;

public class UUIDUtil {

    public static UUID getOrCreateUUID(UUID uuid) {
        return uuid == null ? UUID.randomUUID() : uuid;
    }
}
