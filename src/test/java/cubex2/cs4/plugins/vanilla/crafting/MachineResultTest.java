package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MachineResultTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_deserializer_onlyItem()
    {
        Map<String, MachineResult> map = gson.fromJson("{ \"result\": \"minecraft:dirt\" }", new TypeToken<Map<String, MachineResult>>() {}.getType());

        MachineResult result = map.get("result");

        assertSame(Item.getItemFromBlock(Blocks.DIRT), result.item.getItemStack().getItem());
    }

    @Test
    public void test_deserializer_withChance()
    {
        MachineResult result = gson.fromJson("{ \"item\": \"minecraft:dirt\", \"amount\":5, \"chance\": 0.5 }", MachineResult.class);

        ItemStack stack = result.item.getItemStack();
        assertSame(Item.getItemFromBlock(Blocks.DIRT), stack.getItem());
        assertEquals(5, stack.stackSize);
        assertEquals(0.5f, result.chance, 0.001f);
    }

}