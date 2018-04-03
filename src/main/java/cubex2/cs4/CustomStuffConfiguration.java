package cubex2.cs4;

import net.minecraftforge.common.config.Config;
import static net.minecraftforge.common.config.Config.*;

@Config(modid = CustomStuff4.ID)
public class CustomStuffConfiguration {
    @Comment({
            "Enables the experimental scripting feature",
            "This is not yet ready to be used for real, the API is incomplete and might change.",
            "Be prepared for major bugs, use at your own risk!"
    })
    @RequiresMcRestart
    public static boolean ScriptEnabled = true;



    private static class ScriptConfig {
        public int CpuLimit;
        public int MemoryLimit;
    }
}
