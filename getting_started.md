---
layout: default
---

# Create Mod

To get started, you need to create a new folder in the mods directory of you minecraft installation. In that folder create a file called _cs4mod.json_ with the following content:
```json
{  
	"id": "mymod",
	"name": "My Mod",
	"version": "1.0.0"
}
```
Replace _mymod_ with the id for your mod. The id must only contain lowercase letters (a-z), numbers (0-9) and underscores.  
Replace _My Mod_ with the name for your mod.  
Replace _1.0.0_ with the version of your mod.

You can now start Minecraft, which will create the necessary files for your mod to work. Note that your mod will only appear in the mod list after the second start of Minecraft. There should now be a folder called _cs4mod_ next to _cs4mod.json_.  
That's it. You've set everything up.

To change the version of your mod, edit the _cs4mod.json_ file, delete the _cs4mod_ folder and start Minecraft. Make sure to not load a world before the second start of Minecraft as your mod will not be loaded until then.

# Adding Content

To add content, you'll have to create a new file called _main.json_. This file contains everything that is being added to the game. However you can outsource anything to another file.
Every json file consists of named lists, where each entry in these lists adds one or more things of the same type, for example shaped recipes.

For a simple recipe the file might look like this:

```json
{
  "content": [
    {
      "type": "shapedRecipe",
      "entries": [
        {
          "shape": ["AA", "BX"],
          "items": {
            "A": "minecraft:coal",
            "B": "minecraft:diamond"
          },
          "result": "minecraft:apple"
        }
      ]
    }
  ]
}
```
	
The name of the list, _content_ here, is completely arbitrary and just adds a little more structure. You could have a list for recipes and another one for fuel.  
That's all you need to do add some content.

If you want to use multiple files, for example to have recipes in its own file, you can do this:

```json
{
  "content": [
    {
      "file": "recipes.json"
    }
  ]
}
```
	
Files loaded that way work exactly the same as _main.json_. This means you can a file load another file which then itself loads some files. For example, you can let _recipes.json_ load _crafting\_recipes.json_ and _smelting\_recipes.json_.

You can also add conditions to a content entry to only load that content if the conditions are met. You can, for example, only add certain recipes if a specific mod is loaded:

```json
{
  "content": [
    {
      "file": "recipes.json",
      "requireModsLoaded": ["chesttransporter"],
      "requireModsNotLoaded": ["morefurnaces"]
    }
  ]
}
```
	
This works for both _file_ and _entries_.  
The content is only loaded if all conditions are met. In the example above, _chesttransporter_ has to be loaded and _morefurnaces_ must not.