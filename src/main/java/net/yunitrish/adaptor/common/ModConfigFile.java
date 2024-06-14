package net.yunitrish.adaptor.common;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.Adaptor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ModConfigFile {

    private static final Path FABRIC_CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    public final File file;
    public final String FILENAME;
    public ModConfig config = null;

    public ModConfigFile(String fileName) throws IOException {
        file = new File(FABRIC_CONFIG_DIR.toFile(), fileName);

        this.FILENAME = fileName;
        if (file.exists()) {
            config = read();
        } else {
            if (file.createNewFile() && file.setReadable(true) && file.setWritable(true)) {
                config = new ModConfig();
                write();
            } else {
                Adaptor.LOGGER.info("Error creating config file");
            }
        }
    }

    public ModConfig read() {
        try {
            FileReader reader = new FileReader(file);
            config = new Gson().fromJson(reader, ModConfig.class);
            reader.close();
            return config;
        } catch (IOException ignored) {
            return new ModConfig();
        }
    }

    public String beautify(String string) {
        StringBuilder temp = new StringBuilder();
        int tabCounter = 0;
        char[] array = string.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char it = array[i];
            if (it == '{') {
                tabCounter += 1;
                temp.append(it).append("\n");
                if (tabCounter > 0) temp.append("\t".repeat(tabCounter));
            } else if (it == ',') {
                temp.append(it).append("\n");
                if (tabCounter > 0) temp.append("\t".repeat(tabCounter));
            } else {
                temp.append(it);
            }
            if (i + 1 < array.length) {
                if (array[i + 1] == '}') {
                    temp.append("\n");
                    tabCounter -= 1;
                    if (tabCounter > 0) temp.append("\t".repeat(tabCounter));
                }
            }
        }
        return temp.toString();
    }

    public void write() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(beautify(new Gson().toJson(config)));
            writer.close();
        } catch (IOException ignored) {
            config = new ModConfig();
            write();
        }
    }

    public void addBindData(String discordId, String minecraftId) {
        ServerPlayerEntity player = ModConfig.getPlayerFromMinecraftId(minecraftId);
        UserData userData = new UserData();
        userData.minecraftId = minecraftId;
        userData.uuid = player.getUuid().toString();
        userData.customName = minecraftId;
        config.bindData.put(discordId, userData);
        write();
    }

    public void addBindData(String discordId, String minecraftId, String customName) {
        ServerPlayerEntity player = ModConfig.getPlayerFromMinecraftId(minecraftId);
        UserData userData = new UserData();
        userData.minecraftId = minecraftId;
        userData.uuid = player.getUuid().toString();
        userData.customName = customName;
        config.bindData.put(discordId, userData);

        player.setCustomNameVisible(true);
        player.setCustomName(Text.of(userData.customName));

        write();
    }

    public void addBindData(String discordId, UserData userData) {
        config.bindData.put(discordId, userData);
        ServerPlayerEntity player = ModConfig.getPlayerFromMinecraftId(userData.minecraftId);
        player.setCustomNameVisible(true);
        player.setCustomName(Text.of(userData.customName));
        write();
    }

    public boolean isBindPlayer(String discordId) {
        return config.bindData.containsKey(discordId);
    }
}
