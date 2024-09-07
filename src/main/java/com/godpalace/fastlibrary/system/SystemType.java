package com.godpalace.fastlibrary.system;

public enum SystemType {
    WINDOWS,
    LINUX,
    MACOS,
    OTHER;

    public static SystemType getCurrentSystemType() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return WINDOWS;
        } else if (osName.contains("linux")) {
            return LINUX;
        } else if (osName.contains("mac")) {
            return MACOS;
        } else {
            return OTHER;
        }
    }
}
