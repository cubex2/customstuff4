package cubex2.cs4.plugins.vanilla;

import com.google.gson.reflect.TypeToken;
import cubex2.cs4.CommonProxy;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.*;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipeDeserializer;
import cubex2.cs4.plugins.vanilla.crafting.MachineResult;
import cubex2.cs4.plugins.vanilla.gui.ItemFilter;
import cubex2.cs4.plugins.vanilla.gui.ItemFilterDeserializer;
import cubex2.cs4.plugins.vanilla.gui.ProgressBar;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleCrafting;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleInventory;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleMachine;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleTank;
import cubex2.cs4.util.ArrayDeserializer;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

@CS4Plugin
public class VanillaPlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
        registry.registerDeserializer(String[].class, new ArrayDeserializer<>(String[]::new, String.class));
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
        registry.registerDeserializer(Color.class, new ColorDeserializer(CustomStuff4.contentRegistry));
        registry.registerDeserializer(ProgressBar.Direction.class, ProgressBar.Direction.DESERIALIZER);
        registry.registerDeserializer(MachineResult.class, MachineResult.DESERIALIZER);
        registry.registerDeserializer(ItemFilter.class, new ItemFilterDeserializer());
        registry.registerDeserializer(EnumAction.class, new EnumActionDeserializer());
        registry.registerDeserializer(WrappedFluidStack.class, new WrappedFluidStackDeserializer());
        registry.registerDeserializer(MachineRecipeImpl.class, new MachineRecipeDeserializer());
        registry.registerDeserializer(AxisAlignedBB.class, new AxisAlignedBBDeserializer());
        registry.registerDeserializer(BlockDrop.class, new BlockDropDeserializer());
        registry.registerDeserializer(BlockDrop[].class, new ArrayDeserializer<>(BlockDrop[]::new, BlockDrop.class));
        registry.registerDeserializer(BlockTint.class, new BlockTintDeserializer(CustomStuff4.contentRegistry));
        registry.registerDeserializer(BlockRenderLayer.class, new BlockRenderLayerDeserializer());
        registry.registerDeserializer(EnumPlantType.class, new EnumPlantTypeDeserializer());
        registry.registerDeserializer(EnumPlantType[].class, new ArrayDeserializer<>(EnumPlantType[]::new, EnumPlantType.class));
        registry.registerDeserializer(PathNodeType.class, new PathNodeTypeDeserializer());
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
        registry.registerDeserializer(new TypeToken<Attribute<AxisAlignedBB>>() {}.getType(), Attribute.deserializer(AxisAlignedBB.class));
        registry.registerDeserializer(new TypeToken<Attribute<BlockDrop>>() {}.getType(), Attribute.deserializer(BlockDrop.class));
        registry.registerDeserializer(new TypeToken<Attribute<BlockDrop[]>>() {}.getType(), Attribute.deserializer(BlockDrop[].class));
        registry.registerDeserializer(new TypeToken<Attribute<BlockTint>>() {}.getType(), Attribute.deserializer(BlockTint.class));
        registry.registerDeserializer(new TypeToken<Attribute<BlockRenderLayer>>() {}.getType(), Attribute.deserializer(BlockRenderLayer.class));
        registry.registerDeserializer(new TypeToken<Attribute<EnumPlantType>>() {}.getType(), Attribute.deserializer(EnumPlantType.class));
        registry.registerDeserializer(new TypeToken<Attribute<EnumPlantType[]>>() {}.getType(), Attribute.deserializer(EnumPlantType[].class));
        registry.registerDeserializer(new TypeToken<Attribute<PathNodeType>>() {}.getType(), Attribute.deserializer(PathNodeType.class));

        registry.registerContentType("shapedRecipe", ShapedRecipe.class);
        registry.registerContentType("shapelessRecipe", ShapelessRecipe.class);

        registry.registerContentType("smeltingRecipe", SmeltingRecipe.class);
        registry.registerContentType("fuel", Fuel.class);
        registry.registerContentType("oreDict", OreDictionaryEntry.class);
        registry.registerContentType("toolTip", ToolTip.class, Side.CLIENT);
        registry.registerContentType("guiModifier", GuiModifier.class, Side.CLIENT);
        registry.registerContentType("fluidModifier", FluidModifier.class);
        registry.registerContentType("creativeTab", CreativeTab.class);

        registry.registerContentType("item:simple", ContentItemSimple.class);
        registry.registerContentType("item:axe", ContentItemAxe.class);
        registry.registerContentType("item:pickaxe", ContentItemPickaxe.class);
        registry.registerContentType("item:shovel", ContentItemShovel.class);
        registry.registerContentType("item:sword", ContentItemSword.class);
        registry.registerContentType("item:food", ContentItemFood.class);
        registry.registerContentType("item:fluidContainer", ContentItemFluidContainer.class);
        registry.registerContentType("item:shears", ContentItemShears.class);
        registry.registerContentType("item:seeds", ContentItemSeeds.class);

        registry.registerContentType("block:simple", ContentBlockSimple.class);
        registry.registerContentType("block:orientable:vertical", ContentBlockOrientableVertical.class);
        registry.registerContentType("block:orientable:horizontal", ContentBlockOrientableHorizontal.class);
        registry.registerContentType("block:orientable:directional", ContentBlockOrientableDirectional.class);
        registry.registerContentType("block:fence", ContentBlockFence.class);
        registry.registerContentType("block:stairs", ContentBlockStairs.class);
        registry.registerContentType("block:slab", ContentBlockSlab.class);
        registry.registerContentType("block:fluid", ContentBlockFluid.class);
        registry.registerContentType("block:carpet", ContentBlockCarpet.class);
        registry.registerContentType("block:snow", ContentBlockSnow.class);
        registry.registerContentType("block:crops", ContentBlockCrops.class);
        registry.registerContentType("block:fenceGate", ContentBlockFenceGate.class);
        registry.registerContentType("block:wall", ContentBlockWall.class);
        registry.registerContentType("block:trapDoor", ContentBlockTrapDoor.class);
        registry.registerContentType("block:torch", ContentBlockTorch.class);
        registry.registerContentType("block:button", ContentBlockButton.class);

        registry.registerContentType("worldGen:ore", WorldGenOre.class);

        registry.registerContentType("tileentity:simple", ContentTileEntitySimple.class);

        registry.registerTileEntityModule("inventory", TileEntityModuleInventory.Supplier.class);
        registry.registerTileEntityModule("crafting", TileEntityModuleCrafting.Supplier.class);
        registry.registerTileEntityModule("machine", TileEntityModuleMachine.Supplier.class);
        registry.registerTileEntityModule("tank", TileEntityModuleTank.Supplier.class);

        registry.registerContentType("gui:container", ContentGuiContainer.class);

        registry.registerContentType("machineRecipe", MachineRecipeImpl.class);
        registry.registerContentType("machineFuel", MachineFuelImpl.class);

        registry.registerContentType("imc", IMCBase.class);
        registry.registerDeserializer(IMCBase.class, new IMCDeserializer());

        registry.registerBlockTint("none", BlockTint.WHITE);
        registry.registerBlockTint("foliage", CustomStuff4.getProxy().orElseGet(CommonProxy::new).getFoliageTint());
        registry.registerBlockTint("grass", CustomStuff4.getProxy().orElseGet(CommonProxy::new).getGrassTint());
        registry.registerBlockTint("water", CustomStuff4.getProxy().orElseGet(CommonProxy::new).getWaterTint());

        registry.registerColor("black", 0xff000000);
        registry.registerColor("white", 0xffffffff);
        registry.registerColor("red", 0xffff0000);
        registry.registerColor("lime", 0xff00ff00);
        registry.registerColor("blue", 0xff0000FF);
        registry.registerColor("yellow", 0xffffff00);
        registry.registerColor("aqua", 0xff00ffff);
        registry.registerColor("magenta", 0xffff00ff);
        registry.registerColor("silver", 0xffc0c0c0);
        registry.registerColor("gray", 0xff808080);
        registry.registerColor("maroon", 0xff800000);
        registry.registerColor("olive", 0xff808000);
        registry.registerColor("green", 0xff008000);
        registry.registerColor("purple", 0xff800080);
        registry.registerColor("teal", 0xff008080);
        registry.registerColor("navy", 0xff000080);
        registry.registerColor("foliagePine", 0x619961);
        registry.registerColor("foliageBirch", 0x80a755);
        registry.registerColor("foliageBasic", 0x48b518);
    }
}
