//This file contains the API used to link javascript to minecraft.

function include(name){
    includer.include(name);
}

function require(name){
    return includer.require(name);
}

//exporting is done by setting a variable named "export".

var EventHandlerObject = function(){

}

EventHandlerObject.prototype.register = function(eventName,handler){
    if(typeof(this[eventName]) == "undefined"){
        this[eventName] = []
    }
    this[eventName].push(handler);
}

var EventHandler = new EventHandlerObject();