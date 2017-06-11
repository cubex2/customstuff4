package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.data.SimpleContent;

abstract class IMCBase<T> extends SimpleContent
{
    String modId;
    String key;
    T value;
}
