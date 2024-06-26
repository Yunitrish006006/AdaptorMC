package net.yunitrish.adaptor.common;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.Adaptor;

public record VersionChecker(String modVersion) implements CustomPayload {

    public static final String TargetModId = Adaptor.MOD_ID;

    public static final VersionChecker DEFAULT = new VersionChecker(versionGet(TargetModId));

    public static final CustomPayload.Id<VersionChecker> ID = new CustomPayload.Id<>(Adaptor.id("version_check"));
    public static final PacketCodec<RegistryByteBuf, VersionChecker> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, VersionChecker::modVersion,
            VersionChecker::new
    );

    public static String versionGet(String modId) {
        if (FabricLoader.getInstance().getModContainer(modId).isPresent())
            return FabricLoader.getInstance().getModContainer(modId).get().getMetadata().getVersion().toString();
        return "version not found";
    }

    public static void registerClient() {
        ClientPlayConnectionEvents.JOIN.register(
                Adaptor.id("version_check"),
                (handler, sender, client) -> ClientPlayNetworking.send(DEFAULT)
        );
    }

    public static void registerMain() {
        PayloadTypeRegistry.playC2S().register(VersionChecker.ID, VersionChecker.CODEC);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(
                VersionChecker.ID,
                (versionChecker, context) -> {
                    if (!versionChecker.modVersion().equals(versionGet(TargetModId))) {
                        context.player().networkHandler.disconnect(Text.translatable(
                                "adaptor.server.version_not_match",
                                TargetModId,
                                versionGet(TargetModId),
                                versionChecker.modVersion()
                        ));
                    }
                });
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
