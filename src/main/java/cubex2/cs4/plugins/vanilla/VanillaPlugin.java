package cubex2.cs4.plugins.vanilla;

import com.google.gson.reflect.TypeToken;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.*;
import cubex2.cs4.plugins.vanilla.crafting.MachineResult;
import cubex2.cs4.plugins.vanilla.gui.ItemFilter;
import cubex2.cs4.plugins.vanilla.gui.ItemFilterDeserializer;
import cubex2.cs4.plugins.vanilla.gui.ProgressBar;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleCrafting;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleInventory;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleMachine;
import cubex2.cs4.util.IntRange;
import cubex2.cs4.util.JsonHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

@CS4Plugin
public class VanillaPlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
        registry.registerDeserializer(String[].class, (json, typeOfT, context) -> JsonHelper.arrayFromElement(json));
        registry.registerDeserializer(ResourceLocation.class, new ResourceLocationDeserializer());
        registry.registerDeserializer(WrappedItemStack.class, new WrappedItemStackDeserializer());
        registry.registerDeserializer(RecipeInput.class, new RecipeInputDeserializer());
        registry.registerDeserializer(NBTTagCompound.class, new NBTTagCompoundDeserializer());
        registry.registerDeserializer(Length.class, new LengthDeserializer());
        registry.registerDeserializer(WrappedToolMaterial.class, new ToolMaterialDeserializer());
        registry.registerDeserializer(Material.class, new MaterialDeserializer());
        registry.registerDeserializer(SoundType.class, new SoundTypeDeserializer());
        registry.registerDeserializer(IntRange.class, new IntRangeDeserializer());
        registry.registerDeserializer(WrappedPotionEffect.class, new PotionEffectDeserializer());
        registry.registerDeserializer(MapColor.class, new MapColorDeserializer());
        registry.registerDeserializer(WrappedBlockState.class, new WrappedBlockStateDeserializer());
        registry.registerDeserializer(TileEntityModuleSupplier.class, new TileEntityModuleSupplierDeserializer(CustomStuff4.contentRegistry));
        registry.registerDeserializer(EnumFacing.class, new EnumFacingDeserializer());
        registry.registerDeserializer(Color.class, new ColorDeserializer());
        registry.registerDeserializer(ProgressBar.Direction.class, ProgressBar.Direction.DESERIALIZER);
        registry.registerDeserializer(MachineResult.class, MachineResult.DESERIALIZER);
        registry.registerDeserializer(ItemFilter.class, new ItemFilterDeserializer());
        registry.registerDeserializer(EnumAction.class, new EnumActionDeserializer());
        registry.registerDeserializer(new TypeToken<Map<String, TileEntityModuleSupplier>>() {}.getType(), new NamedMapDeserializer<>(TileEntityModuleSupplier.class));
        registry.registerDeserializer(new TypeToken<Attribute<ResourceLocation>>() {}.getType(), Attribute.deserializer(ResourceLocation.class));
        registry.registerDeserializer(new TypeToken<Attribute<String>>() {}.getType(), Attribute.deserializer(String.class));
        registry.registerDeserializer(new TypeToken<Attribute<Float>>() {}.getType(), Attribute.deserializer(Float.class));
        registry.registerDeserializer(new TypeToken<Attribute<Integer>>() {}.getType(), Attribute.deserializer(Integer.class));
        registry.registerDeserializer(new TypeToken<Attribute<SoundType>>() {}.getType(), Attribute.deserializer(SoundType.class));
        registry.registerDeserializer(new TypeToken<Attribute<IntRange>>() {}.getType(), Attribute.deserializer(IntRange.class));
        registry.registerDeserializer(new TypeToken<Attribute<String[]>>() {}.getType(), Attribute.deserializer(String[].class));
        registry.registerDeserializer(new TypeToken<Attribute<Boolean>>() {}.getType(), Attribute.deserializer(Boolean.class));
        registry.registerDeserializer(new TypeToken<Attribute<WrappedPotionEffect>>() {}.getType(), Attribute.deserializer(WrappedPotionEffect.class));
        registry.registerDeserializer(new TypeToken<Attribute<MapColor>>() {}.getType(), Attribute.deserializer(MapColor.class));
        registry.registerDeserializer(new TypeToken<Attribute<WrappedBlockState>>() {}.getType(), Attribute.deserializer(WrappedBlockState.class));
        registry.registerDeserializer(new TypeToken<Attribute<Color>>() {}.getType(), Attribute.deserializer(Color.class));
        registry.registerDeserializer(new TypeToken<Attribute<WrappedItemStack>>() {}.getType(), Attribute.deserializer(WrappedItemStack.class));
        registry.registerDeserializer(new TypeToken<Attribute<EnumAction>>() {}.getType(), Attribute.deserializer(EnumAction.class));

        registry.registerContentType("shapedRecipe", ShapedRecipe.class);
        registry.registerContentType("shapelessRecipe", ShapelessRecipe.class);

        registry.registerContentType("smeltingRecipe", SmeltingRecipe.class);
        registry.registerContentType("fuel", Fuel.class);
        registry.registerContentType("oreDict", OreDictionaryEntry.class);
        registry.registerContentType("toolTip", ToolTip.class);
        registry.registerContentType("guiModifier", GuiModifier.class);
        registry.registerContentType("fluidModifier", FluidModifier.class);

        registry.registerContentType("item:simple", ContentItemSimple.class);
        registry.registerContentType("item:axe", ContentItemAxe.class);
        registry.registerContentType("item:pickaxe", ContentItemPickaxe.class);
        registry.registerContentType("item:shovel", ContentItemShovel.class);
        registry.registerContentType("item:sword", ContentItemSword.class);
        registry.registerContentType("item:food", ContentItemFood.class);

        registry.registerContentType("block:simple", ContentBlockSimple.class);
        registry.registerContentType("block:orientable:vertical", ContentBlockOrientableVertical.class);
        registry.registerContentType("block:orientable:horizontal", ContentBlockOrientableHorizontal.class);
        registry.registerContentType("block:orientable:directional", ContentBlockOrientableDirectional.class);
        registry.registerContentType("block:fence", ContentBlockFence.class);
        registry.registerContentType("block:stairs", ContentBlockStairs.class);
        registry.registerContentType("block:slab", ContentBlockSlab.class);
        registry.registerContentType("block:fluid", ContentBlockFluid.class);

        registry.registerContentType("worldGen:ore", WorldGenOre.class);

        registry.registerContentType("tileentity:simple", ContentTileEntitySimple.class);

        registry.registerTileEntityModule("inventory", TileEntityModuleInventory.Supplier.class);
        registry.registerTileEntityModule("crafting", TileEntityModuleCrafting.Supplier.class);
        registry.registerTileEntityModule("machine", TileEntityModuleMachine.Supplier.class);

        registry.registerContentType("gui:container", ContentGuiContainer.class);

        registry.registerContentType("machineRecipe", MachineRecipeImpl.class);
        registry.registerContentType("machineFuel", MachineFuelImpl.class);
    }
}
