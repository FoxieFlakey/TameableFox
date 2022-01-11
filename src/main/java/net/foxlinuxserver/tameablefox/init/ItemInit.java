package net.foxlinuxserver.tameablefox.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {
  private static final Logger LOGGER = LogManager.getLogger(ItemInit.class);
  private static List<Item> items = new ArrayList<>();
  private static List<String> itemName = new ArrayList<>();
  private static Map<String, Boolean> itemExistence = new HashMap<>();
  
  //public static final Item ITEM_HELLO_TEA = BlockInit.BLOCK_HELLO_TEA.asItem();
  //public static final Item ITEM_HELLO_FUCHS = BlockInit.BLOCK_HELLO_FUCHS.asItem();
  public static final Item ITEM_FOX_SPAWN_EGG = add("fox_egg", 
                                      new SpawnEggItem(EntityInit.ENTITY_FOX, 
                                                       14005919, 13396256, 
                                                       new Item.Settings()
                                                               .group(ItemGroup.MISC)));
  public static final Item ITEM_CURSED_SHEEP_SPAWN_EGG = add("cursed_sheep_egg", 
                                      new SpawnEggItem(EntityInit.ENTITY_CURSED_SHEEP, 
                                                       0xE7E7E7, 0xFFB5B5, 
                                                       new Item.Settings()
                                                               .group(ItemGroup.MISC)));
  
  public static Item add(String name, Item item) {
    if (itemExistence.get(name) != null && itemExistence.get(name) == true)
      throw new IllegalArgumentException("Item with id of '" + name + "' already exist"); 
    itemName.add(name);
    items.add(item);
    itemExistence.put(name, true);
    return item;
  }
  
  public static Item add(String name, ItemGroup group, Item item) {
    ItemInit.add(name, item);
    
    return item;
  }
  
  public static void register() {
    LOGGER.info("Registering items");
    for (int i = 0; i < items.size(); i++) {
      Item blk = items.get(i);
      Registry.register(Registry.ITEM, new Identifier(TameableFox.MOD_ID, itemName.get(i)), 
                                                                          blk);
    }
    LOGGER.info("Done!");
  }
}
