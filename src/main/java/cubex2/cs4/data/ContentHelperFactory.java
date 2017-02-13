package cubex2.cs4.data;

import cubex2.cs4.CS4Mod;
import cubex2.cs4.api.ContentHelper;

public interface ContentHelperFactory
{
    ContentHelper createHelper(CS4Mod mod);
}
