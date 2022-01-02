package net.foxlinuxserver.tameablefox.entrypoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxlinuxserver.tameablefox.init.EntityInit;

@Environment(EnvType.CLIENT)
public class ClientEntryPoint implements ClientModInitializer {
  private static final Logger LOGGER = LogManager.getLogger(ClientEntryPoint.class.getSimpleName());
  
  @Override
  public void onInitializeClient() {
    LOGGER.info("Client side initialization begin");
    EntityInit.registerClient();
    LOGGER.info("Client side initialization complete");
  }
}

