import mods.mekanism.api.ingredient.ChemicalStackIngredient;

//Adds a Metallurgic Infusing Recipe that uses 10 mB of Fungi Infuse Type to convert any Oak Planks into Crimson Planks.

// <recipetype:mekanism:metallurgic_infusing>.addRecipe(name as string, itemInput as IIngredientWithAmount, chemicalInput as ChemicalStackIngredient, output as IItemStack)

<recipetype:mekanism:metallurgic_infusing>.addRecipe("infuse_planks", <item:minecraft:oak_planks>, ChemicalStackIngredient.from(<chemical:mekanism:fungi> * 10), <item:minecraft:crimson_planks>);
//Alternate implementations of the above recipe are shown commented below. These implementations make use of implicit casting to allow easier calling:
// <recipetype:mekanism:metallurgic_infusing>.addRecipe("infuse_planks", <item:minecraft:oak_planks>, <chemical:mekanism:fungi> * 10, <item:minecraft:crimson_planks>);
// <recipetype:mekanism:metallurgic_infusing>.addRecipe("infuse_planks", <item:minecraft:oak_planks>, ChemicalStackIngredient.from(<chemical:mekanism:fungi> * 10), <item:minecraft:crimson_planks>);
// <recipetype:mekanism:metallurgic_infusing>.addRecipe("infuse_planks", <item:minecraft:oak_planks>, <chemical:mekanism:fungi> * 10, <item:minecraft:crimson_planks>);


//Removes the Metallurgic Infusing Recipe that allows creating Dirt from Sand.

// <recipetype:mekanism:metallurgic_infusing>.removeByName(name as string)

<recipetype:mekanism:metallurgic_infusing>.removeByName("mekanism:metallurgic_infusing/sand_to_dirt");