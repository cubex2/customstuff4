//This file contains the API used to link javascript to minecraft.

function include(name){
    includer.includeMethod(name);
}

function require(name){
    return includer.requireMethod(name);
}

//exporting is done by setting a variable named "export".

var EventHandlerObject = function(){

}

EventHandlerObject.prototype.register = function(eventName,handler){
    if(typeof(this[eventName]) == "undefined"){
        this[eventName] = []
        this[eventName+"Function"] = function(event){
            for each (fun in this[eventName]) {
             fun(event);
            }
        }
    }
    this[eventName].push(handler);
}

var EventHandler = new EventHandlerObject();

//the quick access magic
var $ = EventHandler;