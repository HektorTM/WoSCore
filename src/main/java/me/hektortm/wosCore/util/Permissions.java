package me.hektortm.wosCore.util;

public enum Permissions {

    DISCORD_SEND("discord.send"),

    LOG_VIEW("core.log.view"),
    LOG_WRITE("core.log.write"),

    WARNING_TOGGLE("core.warning.toggle"),
    WARNING_STATUS("core.warning.on");



    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
