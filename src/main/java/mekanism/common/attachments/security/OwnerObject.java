package mekanism.common.attachments.security;

import java.util.Objects;
import java.util.UUID;
import mekanism.api.NBTConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.security.IOwnerObject;
import mekanism.common.registries.MekanismAttachmentTypes;
import mekanism.common.util.ItemDataUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.text.OwnerDisplay;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

@NothingNullByDefault
public class OwnerObject implements IOwnerObject {

    private final IAttachmentHolder attachmentHolder;
    @Nullable
    UUID ownerUUID;

    public OwnerObject(IAttachmentHolder attachmentHolder) {
        this.attachmentHolder = attachmentHolder;
        if (this.attachmentHolder instanceof ItemStack stack && !stack.isEmpty()) {
            loadLegacyData(stack);
        }
    }

    @Deprecated//TODO - 1.21?: Remove this way of loading legacy data
    protected void loadLegacyData(ItemStack stack) {
        if (ItemDataUtils.hasData(stack, NBTConstants.OWNER_UUID, Tag.TAG_INT_ARRAY)) {
            ownerUUID = ItemDataUtils.getUniqueID(stack, NBTConstants.OWNER_UUID);
            //Remove the legacy data now that it has been parsed and loaded
            ItemDataUtils.removeData(stack, NBTConstants.OWNER_UUID);
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    @Nullable
    @Override
    public String getOwnerName() {
        UUID owner = getOwnerUUID();
        if (owner != null) {
            //Do our best effort to figure out what the owner's name is, but it is possible we won't be able to calculate one
            return OwnerDisplay.getOwnerName(MekanismUtils.tryGetClientPlayer(), owner, null);
        }
        return null;
    }

    @Override
    public void setOwnerUUID(@Nullable UUID owner) {
        if (attachmentHolder.hasData(MekanismAttachmentTypes.FREQUENCY_AWARE)) {
            //If the object happens to be a frequency aware object reset the frequency when the owner changes
            attachmentHolder.getData(MekanismAttachmentTypes.FREQUENCY_AWARE).setFrequency(null);
        }
        this.ownerUUID = owner;
    }

    public boolean isCompatible(OwnerObject other) {
        if (this == other) {
            return true;
        } else if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(ownerUUID, other.ownerUUID);
    }

    public static class OwnerOnlyObject extends OwnerObject implements INBTSerializable<IntArrayTag> {

        public OwnerOnlyObject(IAttachmentHolder attachmentHolder) {
            super(attachmentHolder);
        }

        @Nullable
        @Override
        public IntArrayTag serializeNBT() {
            return ownerUUID == null ? null : NbtUtils.createUUID(ownerUUID);
        }

        @Override
        public void deserializeNBT(IntArrayTag nbt) {
            ownerUUID = NbtUtils.loadUUID(nbt);
        }
    }
}