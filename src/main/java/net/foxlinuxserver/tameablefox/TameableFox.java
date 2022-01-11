package net.foxlinuxserver.tameablefox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.foxlinuxserver.tameablefox.init.BlockInit;
import net.foxlinuxserver.tameablefox.init.EntityInit;
import net.foxlinuxserver.tameablefox.init.ItemInit;
import software.bernie.geckolib3.GeckoLib;

// Common initializer
public class TameableFox implements ModInitializer {
  private static final Logger LOGGER = LogManager.getLogger(TameableFox.class.getSimpleName());
  public static final String MOD_ID = "tameablefox";
  
  // Written using vim for sake more RAM lol (no longer valid)
  @Override
  public void onInitialize() {
    LOGGER.info("Initializing GeckoLib");
    GeckoLib.initialize();
    LOGGER.info("Done!");
    
    LOGGER.info("Common initialization begin");
    ItemInit.register(); 
    BlockInit.register();
    EntityInit.register();
    LOGGER.info("Common initialization complete");
  }
}

