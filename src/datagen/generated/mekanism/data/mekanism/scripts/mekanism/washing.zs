import mods.mekanism.api.ingredient.ChemicalStackIngredient;

//Removes the Washing Recipe for cleaning Dirty Uranium Slurry.

// <recipetype:mekanism:washing>.removeByName(name as string)

<recipetype:mekanism:washing>.removeByName("mekanism:processing/uranium/slurry/clean");
//Add back the Washing Recipe that was removed above, this time having it require 10 mB of water to clean 1 mB of Dirty Uranium Slurry instead of 5 mB:

// <recipetype:mekanism:washing>.addRecipe(name as string, fluidInput as CTFluidIngredient, slurryInput as ChemicalStackIngredient, output as ICrTChemicalStack)

<recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, ChemicalStackIngredient.from(<chemical:mekanism:dirty_uranium>), <chemical:mekanism:clean_uranium>);
//Alternate implementations of the above recipe are shown commented below. These implementations make use of implicit casting to allow easier calling:
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, <chemical:mekanism:dirty_uranium>, <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, ChemicalStackIngredient.from(<chemical:mekanism:dirty_uranium>), <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, <chemical:mekanism:dirty_uranium>, <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, ChemicalStackIngredient.from(<chemical:mekanism:dirty_uranium>), <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, <chemical:mekanism:dirty_uranium>, <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, ChemicalStackIngredient.from(<chemical:mekanism:dirty_uranium>), <chemical:mekanism:clean_uranium>);
// <recipetype:mekanism:washing>.addRecipe("cleaning_uranium_slurry", <tag:fluid:minecraft:water> * 10, <chemical:mekanism:dirty_uranium>, <chemical:mekanism:clean_uranium>);

