package cubex2.cs4.plugins;

import cubex2.cs4.api.LoaderPredicate;
import net.minecraftforge.fml.common.Loader;

import java.util.List;

public class ModLoadedPredicate implements LoaderPredicate
{
    @Override
    public boolean getResult(List<String> arguments)
    {
        for (String modId : arguments)
        {
            if (!Loader.isModLoaded(modId))
                return false;
        }

        return true;
    }
}
