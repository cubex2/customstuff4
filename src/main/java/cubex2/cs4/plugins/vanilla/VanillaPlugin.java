package cubex2.cs4.plugins.vanilla;

import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.*;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@CS4Plugin
public class VanillaPlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
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
        registry.registerDeserializer(new TypeToken<MetadataAttribute<ResourceLocation>>() {}.getType(), MetadataAttribute.deserializer(ResourceLocation.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<String>>() {}.getType(), MetadataAttribute.deserializer(String.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<Float>>() {}.getType(), MetadataAttribute.deserializer(Float.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<Integer>>() {}.getType(), MetadataAttribute.deserializer(Integer.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<SoundType>>() {}.getType(), MetadataAttribute.deserializer(SoundType.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<IntRange>>() {}.getType(), MetadataAttribute.deserializer(IntRange.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<String[]>>() {}.getType(), MetadataAttribute.deserializer(String[].class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<Boolean>>() {}.getType(), MetadataAttribute.deserializer(Boolean.class));
        registry.registerDeserializer(new TypeToken<MetadataAttribute<WrappedPotionEffect>>() {}.getType(), MetadataAttribute.deserializer(WrappedPotionEffect.class));

        registry.registerDeserializer(ShapedRecipe.class, ShapedRecipe.DESERIALIZER);
        registry.registerContentType("shapedRecipe", ShapedRecipe.class);

        registry.registerDeserializer(ShapelessRecipe.class, ShapelessRecipe.DESERIALIZER);
        registry.registerContentType("shapelessRecipe", ShapelessRecipe.class);

        registry.registerContentType("smeltingRecipe", SmeltingRecipe.class);
        registry.registerContentType("fuel", Fuel.class);
        registry.registerContentType("oreDict", OreDictionaryEntry.class);
        registry.registerContentType("toolTip", ToolTip.class);
        registry.registerContentType("guiModifier", GuiModifier.class);

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

        registry.registerContentType("worldGen:ore", WorldGenOre.class);
    }
}
