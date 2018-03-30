package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.tileentity.CSTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        public TestTile(String contentId)
        {
        }

        @Override
        public ContentTileEntityBase getContent()
        {
            return null;
        }
    }
}
