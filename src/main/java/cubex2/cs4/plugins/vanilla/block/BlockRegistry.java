package cubex2.cs4.plugins.vanilla.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Blocks from CS4 mods are registered here right after they have been initialized. Only use this if you need a reference
 * to a block before the registry event for blocks is fired.
 */
public class BlockRegistry
{
    public static final BlockRegistry INSTANCE = new BlockRegistry();

    private final Map<ResourceLocation, Block> blocks = Maps.newHashMap();

    private BlockRegistry()
    {
    }

    public void register(Block block)
    {
        blocks.put(block.getRegistryName(), block);
    }

    public boolean contains(ResourceLocation registryName)
    {
        return blocks.containsKey(registryName);
    }

    @Nullable
    public Block get(ResourceLocation registryName)
    {
        return blocks.get(registryName);
    }
}
