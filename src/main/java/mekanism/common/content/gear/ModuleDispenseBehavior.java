package mekanism.common.content.gear;

import java.util.Collection;
import java.util.List;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.ICustomModule.ModuleDispenseResult;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModuleDispenseBehavior extends OptionalDispenseItemBehavior {

    @NotNull
    @Override
    protected ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
        //Note: We don't check if the stack is empty as it is never checked in vanilla's ones, and we also
        // don't check if the stack is a module container as we only register this dispense behavior on stacks that are
        setSuccess(true);
        ModuleDispenseResult result = performBuiltin(source, stack);
        if (result == ModuleDispenseResult.HANDLED) {
            return stack;
        }
        boolean preventDrop = result == ModuleDispenseResult.FAIL_PREVENT_DROP;
        IModuleContainer container = IModuleHelper.INSTANCE.getModuleContainerNullable(stack);
        Collection<? extends IModule<?>> modules = container != null ? container.modules() : List.of();
        for (IModule<?> module : modules) {
            if (module.isEnabled()) {
                result = onModuleDispense(module, source);
                if (result == ModuleDispenseResult.HANDLED) {
                    return stack;
                }
                preventDrop |= result == ModuleDispenseResult.FAIL_PREVENT_DROP;
            }
        }
        if (preventDrop) {
            setSuccess(false);
            return stack;
        }
        //Note: We don't mark it as a "failed" so that it plays to proper sound when it is ejecting the item
        return super.execute(source, stack);
    }

    private <MODULE extends ICustomModule<MODULE>> ModuleDispenseResult onModuleDispense(IModule<MODULE> module, @NotNull BlockSource source) {
        return module.getCustomInstance().onDispense(module, source);
    }

    protected ModuleDispenseResult performBuiltin(@NotNull BlockSource source, @NotNull ItemStack stack) {
        return ModuleDispenseResult.DEFAULT;
    }
}