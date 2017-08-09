package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.BlockTint;

import javax.annotation.Nullable;

public interface BlockTintRegistry
{
    @Nullable
    BlockTint getBlockTint(String name);
}
