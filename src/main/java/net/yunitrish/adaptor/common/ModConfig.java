package net.yunitrish.adaptor.common;

import net.minecraft.server.network.ServerPlayerEntity;
import net.yunitrish.adaptor.AdaptorServer;
import org.spongepowered.include.com.google.gson.JsonElement;

import java.util.Map;

public class ModConfig extends JsonElement {
    public String token = "YOUR-TOKEN-HERE";
    public Map<String, String> bindData = Map.of("discordId", "minecraftID");

    public ModConfig() {
    }

    public static ServerPlayerEntity getPlayerFromDiscordId(String discordId) {
        String minecraftId = getMinecraftId(discordId);
        return AdaptorServer.modServer.getPlayerManager().getPlayer(minecraftId);
    }

    public static ServerPlayerEntity getPlayerFromMinecraftId(String minecraftId) {
        return AdaptorServer.modServer.getPlayerManager().getPlayer(minecraftId);
    }

    public static String getMinecraftId(String discordId) {
        return AdaptorServer.data.config.bindData.get(discordId);
    }

    public boolean isMinecraftIdInDataBind(String minecraftId) {
        return bindData.containsValue(minecraftId);
    }

    public String getFirstMatchDiscordId(String minecraftId) {
        for (Map.Entry<String, String> i : bindData.entrySet()) {
            if (minecraftId.equals(i.getValue())) {
                return i.getKey();
            }
        }
        return null;
    }
}
