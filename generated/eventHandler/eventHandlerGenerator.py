#!/usr/bin/env python
# coding=utf-8

import os.path
root = os.path.abspath(os.path.dirname(__file__))

lines = [line.rstrip('\n') for line in open('./classes.txt')]

header = """package cubex2.cs4.script.api;

$imports

import java.util.Collection;

/**
 * @author <a href="mailto:kisandrasgabor@gmail.com">András Kis</a>
 */
//this class is pre-generated. DO NOT modify it, alter the generator.
@EventBusSubscriber
public class EventHandler {
"""

footer = """
}
"""

#Using a collection, to make sure that it can be changed/optimised depending on what we need
template = """

    @SubscribeEvent
    public void $class($class event) {
            
    }
"""

result = ""
imports = ""

for line in lines:
    data = line.split(':', 3)
    event = template.replace("$name", data[0]).replace("$class", data[1])
    imports = imports + data[2]+";\n"
    result = result + event

eventHandlerJava = root+"/../../src/main/java/cubex2/cs4/script/script/api/EventHandler.java"


with open(eventHandlerJava, "w") as text_file:
    text_file.write(result)
