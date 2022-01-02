package net.foxlinuxserver.tameablefox.entrypoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.DedicatedServerModInitializer;

public class ServerEntryPoint implements DedicatedServerModInitializer {
  private static final Logger LOGGER = LogManager.getLogger(ServerEntryPoint.class.getSimpleName());
  
  @Override
  public void onInitializeServer() {
    LOGGER.info("Server side initialization begin");
    
    LOGGER.info("Server side initialization complete");
  }
}
