package cubex2.cs4.plugins.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DamageableShapedOreRecipeTest
{
    @Before
    public void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test()
    {
        doTest(false, false);
        doTest(false, true);
        doTest(true, false);
        doTest(true, true);
    }

    @Test
    public void test_useUpItem()
    {
        ShapedOreRecipe recipe = new DamageableShapedOreRecipe(new int[] {60}, new ItemStack(Blocks.DIRT),
                                                               "A",
                                                               'A', new ItemStack(Items.WOODEN_SWORD));

        InventoryCrafting inv = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn)
            {
                return false;
            }
        }, 3, 3);

        inv.setInventorySlotContents(0, new ItemStack(Items.WOODEN_SWORD));
        assertTrue(recipe.matches(inv, null));

        ItemStack[] remaining = recipe.getRemainingItems(inv);
        assertTrue(remaining[0] == null);
    }

    private void doTest(boolean mirror, boolean enoughDamage)
    {
        ShapedOreRecipe recipe = new DamageableShapedOreRecipe(new int[] {0, 0, enoughDamage ? 5 : 5000, 0}, new ItemStack(Blocks.DIRT),
                                                               "AA", "BA",
                                                               'A', new ItemStack(Blocks.DIRT),
                                                               'B', new ItemStack(Items.WOODEN_SWORD))
                .setMirrored(mirror);

        InventoryCrafting inv = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn)
            {
                return false;
            }
        }, 3, 3);
        inv.setInventorySlotContents(4, new ItemStack(Blocks.DIRT));
        inv.setInventorySlotContents(5, new ItemStack(Blocks.DIRT));
        inv.setInventorySlotContents(mirror ? 8 : 7, new ItemStack(Items.WOODEN_SWORD));
        inv.setInventorySlotContents(mirror ? 7 : 8, new ItemStack(Blocks.DIRT));

        assertSame(enoughDamage, recipe.matches(inv, null));

        if (enoughDamage)
        {
            ItemStack[] remaining = recipe.getRemainingItems(inv);
            assertSame(Items.WOODEN_SWORD, remaining[mirror ? 8 : 7].getItem());
            assertEquals(5, remaining[mirror ? 8 : 7].getItemDamage());
        }
    }
}