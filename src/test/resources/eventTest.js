EventHandler.register("event1",function(writer){
writer.push("event1.1");
})

EventHandler.register("event1",function(writer){
writer.push("event1-2");
})

EventHandler.register("event2",function(writer){
writer.push("event2");
})