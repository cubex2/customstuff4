package cubex2.cs4.asm;

public class ModInfo
{
    String id = "";
    String name = "";
    String version = "";
    String dependencies = "";

    boolean isValid()
    {
        return id != null && id.matches("[0-9a-z_]+") &&
               name != null &&
               version != null &&
               dependencies != null;
    }
}
