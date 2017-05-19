package cubex2.cs4.plugins.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DamageableShapelessOreRecipeTest
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
        DamageableShapelessOreRecipe recipe = new DamageableShapelessOreRecipe(new int[] {60}, new ItemStack(Blocks.DIRT), new ItemStack(Items.WOODEN_SWORD));
        InventoryCrafting inv = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn)
            {
                return false;
            }
        }, 3, 3);
        inv.setInventorySlotContents(3, new ItemStack(Items.WOODEN_SWORD));

        assertTrue(recipe.matches(inv, null));
        ItemStack[] remaining = recipe.getRemainingItems(inv);
        assertTrue(remaining[3] == null);
    }

    private void doTest(boolean inOrder, boolean enoughDamage)
    {
        DamageableShapelessOreRecipe recipe = new DamageableShapelessOreRecipe(new int[] {enoughDamage ? 5 : 5000, 0}, new ItemStack(Blocks.DIRT), new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.APPLE));
        InventoryCrafting inv = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn)
            {
                return false;
            }
        }, 3, 3);
        inv.setInventorySlotContents(inOrder ? 3 : 4, new ItemStack(Items.WOODEN_SWORD));
        inv.setInventorySlotContents(inOrder ? 4 : 3, new ItemStack(Items.APPLE));

        assertSame(enoughDamage, recipe.matches(inv, null));

        if (enoughDamage)
        {
            ItemStack[] remaining = recipe.getRemainingItems(inv);
            assertSame(Items.WOODEN_SWORD, remaining[inOrder ? 3 : 4].getItem());
            assertEquals(5, remaining[inOrder ? 3 : 4].getItemDamage());
        }
    }
}