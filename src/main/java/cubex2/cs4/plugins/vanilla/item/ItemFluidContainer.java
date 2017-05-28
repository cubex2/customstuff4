package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemFluidContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemFluidContainer extends net.minecraftforge.fluids.capability.ItemFluidContainer
{
    private final ContentItemFluidContainer content;

    public ItemFluidContainer(ContentItemFluidContainer content)
    {
        super(content.capacity);
        this.content = content;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull Item itemIn, @Nullable CreativeTabs tab, @Nonnull List<ItemStack> subItems)
    {
        subItems.add(new ItemStack(this));

        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
        {
            if (!fluid.getName().equals("milk"))
            {
                // add all fluids that the bucket can be filled  with
                FluidStack fs = new FluidStack(fluid, content.capacity);
                ItemStack stack = new ItemStack(this);
                FluidHandlerItemStack fluidHandler = new FluidHandlerItemStack(stack, content.capacity);
                if (fluidHandler.fill(fs, true) == fs.amount)
                {
                    subItems.add(stack);
                }
            }
        }
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack)
    {
        FluidStack fluidStack = getFluid(stack);
        if (fluidStack == null)
        {
            return super.getItemStackDisplayName(stack);
        }

        String unloc = this.getUnlocalizedNameInefficiently(stack);

        if (I18n.canTranslate(unloc + "." + fluidStack.getFluid().getName()))
        {
            return I18n.translateToLocal(unloc + "." + fluidStack.getFluid().getName());
        }

        return I18n.translateToLocalFormatted(unloc + ".filled.name", fluidStack.getLocalizedName());
    }

    @Nullable
    public FluidStack getFluid(@Nonnull ItemStack container)
    {
        return FluidStack.loadFluidStackFromNBT(container.getSubCompound("Fluid", false));
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack)
    {
        return new ItemStack(this);
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }
}
