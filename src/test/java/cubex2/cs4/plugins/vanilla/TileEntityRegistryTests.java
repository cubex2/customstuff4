package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.tileentity.CSTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TileEntityRegistryTests
{
    @Test
    public void test_createClass() throws IllegalAccessException, InstantiationException
    {
        Class<? extends TileEntity> clazz = TileEntityRegistry.createClass(TestTile.class, "modid:tileid");
        CSTileEntity tileEntity = (CSTileEntity) clazz.newInstance();

        assertNotNull(tileEntity);
    }

    public abstract static class TestTile extends TileEntity implements CSTileEntity<ContentTileEntityBase>
    {
        @Override
        public ContentTileEntityBase getContent()
        {
            return null;
        }
    }
}
