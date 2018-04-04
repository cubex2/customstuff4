console.log("custom mod is loading");
include("included.js");


EventHandler.register("rightClickBlock",function(event){
    var textComponentString = Java.type("net.minecraft.util.text.TextComponentString");

    var blockname = event.getWorld()
                    .getBlockState(event.getPos())
                    .getBlock()
                    .getRegistryName();

    event.getEntityPlayer()
            .sendMessage(new textComponentString(blockname));
    console.log("clicked again"+blockname);
});