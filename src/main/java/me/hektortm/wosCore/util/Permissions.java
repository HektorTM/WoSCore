package me.hektortm.wosCore.util;

public enum Permissions {

    LOG_VIEW("core.log.view"),
    LOG_WRITE("core.log.write"),

    WARNING_USER("core.warning-user");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
