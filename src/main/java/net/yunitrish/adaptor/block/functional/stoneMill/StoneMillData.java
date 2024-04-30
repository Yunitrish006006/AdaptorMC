package net.yunitrish.adaptor.block.functional.stoneMill;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record StoneMillData(String label) {
    public static final PacketCodec<RegistryByteBuf, StoneMillData> PACKET_CODEC =
            PacketCodec.tuple(PacketCodecs.STRING,StoneMillData::label,StoneMillData::new);
}
