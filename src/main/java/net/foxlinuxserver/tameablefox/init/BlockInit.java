package net.foxlinuxserver.tameablefox.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockInit {
  private static final Logger LOGGER = LogManager.getLogger(BlockInit.class);
  private static List<Block> blocks = new ArrayList<>();
  private static List<String> blockName = new ArrayList<>();
  private static Map<String, Boolean> blockExistence = new HashMap<>();
  
  //public static final Block BLOCK_HELLO_TEA = addWithItem("hello_tea", new Block(
  //  FabricBlockSettings.of(Material.STONE)
  //                     .strength(4.0f)
  //));
  //public static final Block BLOCK_HELLO_FUCHS = addWithItem("hello_fuchs", new Block(
  //  FabricBlockSettings.of(Material.STONE)
  //                    .strength(4.0f)
  //));
  
  public static Block add(String name, Block block) {
    if (blockExistence.get(name) != null && blockExistence.get(name) == true)
      throw new IllegalArgumentException("Block with id of '" + name + "' already exist"); 
    blockName.add(name);
    blocks.add(block);
    blockExistence.put(name, true);
    return block;
  }
  
  public static Block addWithItem(String name, ItemGroup group, Block block) {
    BlockInit.add(name, block);
    ItemInit.add(name, new BlockItem(block, new Item.Settings().group(group)));
    return block;
  }
  
  // Add to default tameable fox item group
  public static Block addWithItem(String name, Block block) {
    BlockInit.add(name, block);
    ItemInit.add(name, GroupInit.TAMEABLEFOX, 
                 new BlockItem(block, new Item.Settings().group(GroupInit.TAMEABLEFOX)));
    return block;
  }
  
  public static void register() {
    LOGGER.info("Registering blocks");
    for (int i = 0; i < blocks.size(); i++) {
      Block blk = blocks.get(i);
      Registry.register(Registry.BLOCK, new Identifier(TameableFox.MOD_ID, blockName.get(i)), 
                                                                           blk);
    }
    LOGGER.info("Done!");
  }
}
