package net.foxlinuxserver.tameablefox;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class TameableFoxSettings implements ModMenu {
  // Enables spawning of derpy foxes (when disabled no derpy foxes spawned naturally
  // except breeding)
  private static boolean isDerpyFoxEnabled = true;
  
  // Enable mixin patch to vanilla foxes (not recommended turning off after
  // creating world with this on)
  private static boolean isMixinPatchEnabled = false;
  
  public static void setDerpyFox(boolean val) {isDerpyFoxEnabled = val;} 
  public static boolean isDerpyFox() {return isDerpyFoxEnabled;} 
  public static void setMixinPatch(boolean val) {isMixinPatchEnabled = val;} 
  public static boolean isMixinPatch() {return isMixinPatchEnabled;} 
  
  private static void addGeneralCategory(ConfigCategory categoryBuilder, ConfigBuilder configBuilder) {
    categoryBuilder.addEntry(configBuilder.entryBuilder()
                                          .startBooleanToggle(new TranslatableText("text.tameablefox.settings.general.derpy_fox"), 
                                                              TameableFoxSettings.isDerpyFox())
                                          .setSaveConsumer(TameableFoxSettings::setDerpyFox)
                                          .setDefaultValue(true)
                                          .build());
  }
  
  private static void addMixinCategory(ConfigCategory categoryBuilder, ConfigBuilder configBuilder) {
    categoryBuilder.addEntry(configBuilder.entryBuilder()
                                          .startBooleanToggle(new TranslatableText("text.tameablefox.settings.mixin.mixin_patch"), 
                                                              TameableFoxSettings.isMixinPatch())
                                          .setSaveConsumer(TameableFoxSettings::setMixinPatch)
                                          .setDefaultValue(false)
                                          .build());
  }
  
  public static Screen getConfigScreen(Screen parent) {
    ConfigBuilder configBuilder = ConfigBuilder.create()  
                                               .setParentScreen(parent)
                                               .setTitle(new TranslatableText("text.tameablefox.settings.title"))
                                               .alwaysShowTabs()
                                               .setDoesConfirmSave(true);
    
    addGeneralCategory(configBuilder.getOrCreateCategory(new TranslatableText("text.tameablefox.settings.general")), configBuilder);
    addMixinCategory(configBuilder.getOrCreateCategory(new TranslatableText("text.tameablefox.settings.mixin")), configBuilder);
    
    return configBuilder.build();
  }
  
  public static void showConfigScreen() {
    MinecraftClient client = MinecraftClient.getInstance();
    client.setScreen(TameableFoxSettings.getConfigScreen(client.currentScreen));
  }
  
  @Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return TameableFoxSettings::getConfigScreen;
	}
}
