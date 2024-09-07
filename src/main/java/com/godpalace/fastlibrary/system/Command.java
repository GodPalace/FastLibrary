package com.godpalace.fastlibrary.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {
    public static String execute(String command, SystemType systemType) {
        try {
            Process p = null;

            if (systemType == SystemType.WINDOWS) {
                p = Runtime.getRuntime().exec("cmd /c \"" + command + "\"");

                if (!command.startsWith("start ")) p.waitFor();
            } else if (systemType == SystemType.LINUX || systemType == SystemType.MACOS) {
                p = Runtime.getRuntime().exec("sh -c \"" + command + "\"");

                if (!command.startsWith("gnome-terminal ")) p.waitFor();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String execute(String command) {
        return execute(command, SystemType.getCurrentSystemType());
    }
}
