# Interchangeable

![Interchangeable Icon](./src/main/resources/assets/interchangeable/icon.png)

## About

Minecraft mod for interchangeable inputs in recipes based on config.

Define `Equivalence Classes` to let a group of items behaves as one another in the inputs of crafting recipes.

## Configuration



A file named `interchangeable.json` needs to exist on the `config` folder of the
Minecraft instance that the mod is loaded on. The mod will automatically create a template
for this file during initialization if the file doesn't exist. That means that if the file
gets corrupted, you can always delete it and the mod will re-create a template for it.

```
{
  "EquivalenceClasses": [
    []
  ],
  "Substitutions": {
    "": []
  },
  "RecipeSpecificEquivalenceClasses": {
    "": []
  }
}
```

`EquivalenceClasses` - Array of String Arrays that allow definition of items by ID. All items in an array will be interchangeable with each other as inputs in crafting recipes.

`Substitutions` - Map of a String to a String Array. The String that precedes the `:` is the ID of the item that you want to be substituted for. Any items identified in the array can take the place of the aforementioned String in recipes.

`RecipeSpecificEquivalenceClasses` - Map of a String to a String Array. The String that precedes the `:` is the ID of the crafting recipe that you want the equivalence class to take place for. The array then acts identically as it would for `EquivalenceClasses` for that specific recipe.

## Issues
If the game crashes or if the interchangeability doesn't seem to have been applied to the recipe, create an issue. You can also join the Timefall discord here: https://discord.gg/EHrWY5ZTYQ where I can provide direct support.

## The Future of Development

There are likely to be some initial bugs as the conditions are a bit complicated. Please report them, give any feature requests, and report any grievances about ease of use in the aforementioned discord. I'm looking to make this as easy to use as possible. More advanced/specific crafting methods and more config specificity will likely be an update in the future. Thank you for your support!

## Rehosting?

**PLEASE DO NOT REHOST THIS MOD**. It is rude, if you need it on somewhere other than
CurseForge, **TALK TO ME**. I will likely put it on Modrinth at some point. Please respect this. I will happily accept
any contributions anyone makes. Feel free to copy a large portion of this mod if you think you can do better!
I just wanted something like this to exist. Just provide a link back to me please. <3

## License

MIT

### Other

Icon drawn by Dream Studio AI art with prompt: Equivalence, symbol, simple, interchangeable, interoperability, exchange two items, horizontal arrows