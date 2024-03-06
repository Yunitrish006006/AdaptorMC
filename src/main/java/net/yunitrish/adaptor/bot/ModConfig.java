package net.yunitrish.adaptor.bot;

import java.util.ArrayList;

public class ModConfig {
    public String token = "YOUR-TOKEN-HERE";
    public String commandPrefix = "mc!";
    public ArrayList<Long> relayChannelIDs = new ArrayList<>();
    public ArrayList<Long> rconUserIDs = new ArrayList<>();
    public boolean disableMentions = true;
    public String discordMessageFormat = "[$USERNAME] $MESSAGE";
    public String chatMessageFormat = "**<$NAME>** $MESSAGE";
    public String systemMessageFormat = "**$MESSAGE**";
}
