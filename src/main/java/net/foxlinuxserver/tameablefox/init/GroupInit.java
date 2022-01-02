package net.foxlinuxserver.tameablefox.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GroupInit {
  public static final ItemGroup TAMEABLEFOX = 
      FabricItemGroupBuilder.create(new Identifier(TameableFox.MOD_ID, "tameablefox_group"))
                            .icon(() -> new ItemStack(Items.SWEET_BERRIES))
                            .build();
  
  public void register() {
    
  }
}
