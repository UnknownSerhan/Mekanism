package mekanism.api.chemical.infuse;

import mekanism.api.SerializationConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.IChemicalTank;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/**
 * Convenience extension to make working with generics easier.
 */
@NothingNullByDefault
public interface IInfusionTank extends IChemicalTank<InfuseType, InfusionStack>, IEmptyInfusionProvider {

    @Override
    default void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt.contains(SerializationConstants.STORED, Tag.TAG_COMPOUND)) {
            setStackUnchecked(InfusionStack.parseOptional(provider, nbt.getCompound(SerializationConstants.STORED)));
        }
    }
}