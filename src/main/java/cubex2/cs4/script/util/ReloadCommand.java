package cubex2.cs4.script.util;

import cubex2.cs4.CustomStuff4;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ReloadCommand extends CommandBase {
    @Override
    public String getName() {
        return "cs4reload";
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return getName();
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        CustomStuff4.scriptHandler.reload();
        iCommandSender.sendMessage(new TextComponentString("Scripts reloaded, any errors have been printed to the console!"));
    }
}
