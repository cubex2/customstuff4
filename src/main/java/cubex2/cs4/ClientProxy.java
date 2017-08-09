package cubex2.cs4;

import cubex2.cs4.api.BlockTint;
import cubex2.cs4.plugins.vanilla.block.CSBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;

import java.util.function.IntFunction;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemModel(Item item, int meta, ResourceLocation modelLocation)
    {
        ModelResourceLocation l = new ModelResourceLocation(modelLocation, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, l);
    }

    @Override
    public void setBlockBiomeTint(Block block, IntFunction<BlockTint> tintTypeForSubtype)
    {
        if (!(block instanceof CSBlock))
            return;

        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        CSBlock csBlock = (CSBlock) block;
        blockColors.registerBlockColorHandler(
                (state, worldIn, pos, tintIndex) -> {
                    if (worldIn == null || pos == null)
                        return ColorizerFoliage.getFoliageColorBasic();

                    return tintTypeForSubtype.apply(csBlock.getSubtype(state)).getMultiplier(worldIn, pos);

                    /*if (tintType == BiomeTintType.FOLIAGE)
                        return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
                    if (tintType == BiomeTintType.GRASS)
                        return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
                    if (tintType == BiomeTintType.WATER)
                        return BiomeColorHelper.getWaterColorAtPos(worldIn, pos);

                    return -1;*/
                }, block);
    }

    @Override
    public void setItemTint(Item item, IntFunction<Integer> itemTint)
    {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                (stack, tintIndex) -> itemTint.apply(stack.getMetadata()), item);
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public BlockTint getFoliageTint()
    {
        return BiomeColorHelper::getFoliageColorAtPos;
    }

    @Override
    public BlockTint getGrassTint()
    {
        return BiomeColorHelper::getGrassColorAtPos;
    }

    @Override
    public BlockTint getWaterTint()
    {
        return BiomeColorHelper::getWaterColorAtPos;
    }
}
