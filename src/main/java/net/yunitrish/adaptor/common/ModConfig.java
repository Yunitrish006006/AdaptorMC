package net.yunitrish.adaptor.common;

import net.minecraft.server.network.ServerPlayerEntity;
import net.yunitrish.adaptor.AdaptorServer;
import org.spongepowered.include.com.google.gson.JsonElement;

import java.util.Map;

public class ModConfig extends JsonElement {
    public String token = "YOUR-TOKEN-HERE";
    public Map<String, UserData> bindData = Map.of("discordId", new UserData());

    public ModConfig() {
    }

    public static ServerPlayerEntity getPlayerFromDiscordId(String discordId) {
        if (AdaptorServer.data.config.bindData.containsKey(discordId)) {
            String minecraftId = getMinecraftId(discordId);
            return AdaptorServer.modServer.getPlayerManager().getPlayer(minecraftId);
        } else {
            return null;
        }
    }

    public static UserData getUserDataFromDiscordId(String discordId) {
        return AdaptorServer.data.config.bindData.get(discordId);
    }

    public static String getCustomNameFromDiscordId(String discordId) {
        return getUserDataFromDiscordId(discordId).customName;
    }

    public static ServerPlayerEntity getPlayerFromMinecraftId(String minecraftId) {
        return AdaptorServer.modServer.getPlayerManager().getPlayer(minecraftId);
    }

    public static String getMinecraftId(String discordId) {
        return AdaptorServer.data.config.bindData.get(discordId).minecraftId;
    }

    public String getFirstMatchDiscordId(String minecraftId) {
        for (Map.Entry<String, UserData> i : bindData.entrySet()) {
            if (i.getValue().minecraftId.equals(minecraftId)) {
                return i.getKey();
            }
        }
        return null;
    }
}
