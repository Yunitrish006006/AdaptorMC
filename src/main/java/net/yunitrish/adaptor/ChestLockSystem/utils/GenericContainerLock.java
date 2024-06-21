package net.yunitrish.adaptor.ChestLockSystem.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.yunitrish.adaptor.common.AdaptorApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GenericContainerLock(UUID owner, List<UUID> members) {
    public static final GenericContainerLock EMPTY = new GenericContainerLock(AdaptorApi.uuidNone, new ArrayList<>());


    public static final Codec<UUID> uuid = Codec.STRING.comapFlatMap(
            str -> {
                try {
                    return DataResult.success(UUID.fromString(str));
                } catch (IllegalArgumentException e) {
                    return DataResult.error(() -> "Invalid UUID: " + str);
                }
            },
            UUID::toString
    );

    public static final Codec<GenericContainerLock> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    uuid.fieldOf("owner").forGetter(GenericContainerLock::owner),
                    Codec.list(uuid).fieldOf("members").forGetter(GenericContainerLock::members)
            ).apply(instance, GenericContainerLock::new)
    );

    public static final String LOCK_OWNER_KEY = "Owner";
    public static final String LOCK_MEMBERS_KEY = "Members";

    public static GenericContainerLock fromNbt(NbtCompound nbt) {
        UUID owner = nbt.containsUuid(LOCK_OWNER_KEY) ? nbt.getUuid(LOCK_OWNER_KEY) : AdaptorApi.uuidNone;
        List<UUID> members = new ArrayList<>();
        if (nbt.contains(LOCK_MEMBERS_KEY, NbtElement.LIST_TYPE)) {
            NbtList memberList = nbt.getList(LOCK_MEMBERS_KEY, NbtElement.STRING_TYPE);
            for (NbtElement memberElement : memberList) {
                members.add(UUID.fromString(memberElement.asString()));
            }
        }
        return new GenericContainerLock(owner, members);
    }

    public boolean canOpen(UUID id) {
        if (this.owner == null) {
            return true;
        }
        return this.owner.equals(id) || this.members.contains(id);
    }

    public void writeNbt(NbtCompound nbt) {
        if (this.owner != null) {
            nbt.putUuid(LOCK_OWNER_KEY, this.owner);
        }
        NbtList memberList = new NbtList();
        for (UUID member : this.members) {
            memberList.add(NbtString.of(member.toString()));
        }
        nbt.put(LOCK_MEMBERS_KEY, memberList);
    }

}
