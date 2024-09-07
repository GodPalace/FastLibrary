package com.godpalace.fastlibrary.system;

public class ProcessUtils {
    public static void killProcess(int pid) {
        try {
            if (pid > 0) {
                SystemType systemType = SystemType.getCurrentSystemType();

                if (systemType == SystemType.WINDOWS) {
                    Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                } else if (systemType == SystemType.LINUX || systemType == SystemType.MACOS) {
                    Runtime.getRuntime().exec("kill -9 " + pid);
                }
            } else {
                System.err.println("Invalid PID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void killProcessByName(String processName) {
        try {
            if (processName!= null &&!processName.isEmpty()) {
                SystemType systemType = SystemType.getCurrentSystemType();

                if (systemType == SystemType.WINDOWS) {
                    Runtime.getRuntime().exec("taskkill /F /IM " + processName);
                } else if (systemType == SystemType.LINUX || systemType == SystemType.MACOS) {
                    Runtime.getRuntime().exec("killall " + processName);
                }
            } else {
                System.err.println("Invalid process name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
