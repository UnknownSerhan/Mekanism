package mekanism.common.integration.crafttweaker.module;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.ModuleData;
import mekanism.common.integration.crafttweaker.CrTConstants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name(CrTConstants.CLASS_MODULE_HELPER)
public class CrTModuleHelper {

    /**
     * Gets all the module types a given item support.
     *
     * @param stack Module container, for example a Meka-Tool or MekaSuit piece.
     *
     * @return Set of supported module types.
     */
    @ZenCodeType.Method
    public static Set<ModuleData<?>> getSupported(ItemStack stack) {
        return container(stack)
              .map(IModuleContainer::supportedTypes)
              .orElse(Set.of());
    }

    /**
     * Helper to get the various items that support a given module type.
     *
     * @param type Module type.
     *
     * @return Set of items that support the given module type.
     */
    @ZenCodeType.Method
    public static Set<Item> getSupported(ModuleData<?> type) {
        return IModuleHelper.INSTANCE.getSupported(type);
    }

    /**
     * Helper method to check if an item has a module installed and the module is enabled.
     *
     * @param stack Module container, for example a Meka-Tool or MekaSuit piece.
     * @param type  Module type.
     *
     * @return {@code true} if the item has the module installed and enabled.
     */
    @ZenCodeType.Method
    public static boolean isEnabled(ItemStack stack, ModuleData<?> type) {
        IModuleContainer container = container(stack).orElse(null);
        return container != null && container.hasEnabled(type);
    }

    /**
     * Helper method to try and load a module from an item.
     *
     * @param stack Module container, for example a Meka-Tool or MekaSuit piece.
     * @param type  Module type.
     *
     * @return Module, or {@code null} if no module of the given type is installed.
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <MODULE extends ICustomModule<MODULE>> IModule<MODULE> load(ItemStack stack, ModuleData<MODULE> type) {
        IModuleContainer container = container(stack).orElse(null);
        return container == null ? null : container.get(type);
    }

    /**
     * Gets a list of all modules on an item stack.
     *
     * @param stack Module container, for example a Meka-Tool or MekaSuit piece.
     *
     * @return List of modules on an item, or an empty list if the item doesn't support modules.
     */
    @ZenCodeType.Method
    @SuppressWarnings({"rawtypes"})
    public static List<IModule> loadAll(ItemStack stack) {
        //ZenCode does not like ? extends IModule<?> so we need to just cast it to a type without any generics specified
        return container(stack)
              .<List<IModule>>map(container -> new ArrayList<>(container.modules()))
              .orElse(List.of());
    }

    /**
     * Gets all the module types on an item stack.
     *
     * @param stack Module container, for example a Meka-Tool or MekaSuit piece.
     *
     * @return Module types on an item.
     */
    @ZenCodeType.Method
    public static Set<ModuleData<?>> loadAllTypes(ItemStack stack) {
        return container(stack)
              .map(IModuleContainer::moduleTypes)
              .orElse(Set.of());
    }

    private static Optional<? extends IModuleContainer> container(ItemStack stack) {
        return Optional.ofNullable(IModuleHelper.INSTANCE.getModuleContainerNullable(stack));
    }
}