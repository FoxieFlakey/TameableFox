package net.foxlinuxserver.tameablefox.entity.fox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ModelFox extends AnimatedGeoModel<EntityFox> {
  private static final Identifier ANIMATION = new Identifier(TameableFox.MOD_ID, "animations/fox.animation.json");
  private static final Identifier MODEL = new Identifier(TameableFox.MOD_ID, "geo/fox.geo.json");
  private static final Identifier TEXTURE_ARCTIC_FOX = new Identifier(TameableFox.MOD_ID, "textures/entity/arctic_fox.png");
  private static final Identifier TEXTURE_RED_FOX = new Identifier(TameableFox.MOD_ID, "textures/entity/fox.png");
  
  private static final Identifier DERP_ANIMATION = new Identifier(TameableFox.MOD_ID, "animations/fox.animation.json");
  private static final Identifier DERP_MODEL = new Identifier(TameableFox.MOD_ID, "geo/fox_derp.geo.json");
  private static final Identifier DERP_TEXTURE_ARCTIC_FOX = new Identifier(TameableFox.MOD_ID, "textures/entity/arctic_fox_derp.png");
  private static final Identifier DERP_TEXTURE_RED_FOX = new Identifier(TameableFox.MOD_ID, "textures/entity/fox_derp.png");
  
  @Override
  public Identifier getAnimationFileLocation(EntityFox entity) {
    return entity.isDerpy() ? DERP_ANIMATION : ANIMATION;
  }
  
  @Override
  public Identifier getModelLocation(EntityFox entity) {
    return entity.isDerpy() ? DERP_MODEL : MODEL;
  }
  
  @Override
  public Identifier getTextureLocation(EntityFox entity) {
    switch (entity.getFoxType()) {
      case SNOW: return entity.isDerpy() ? DERP_TEXTURE_ARCTIC_FOX : TEXTURE_ARCTIC_FOX;
      case RED: return entity.isDerpy() ? DERP_TEXTURE_RED_FOX: TEXTURE_RED_FOX;
      
      default: return entity.isDerpy() ? DERP_TEXTURE_RED_FOX: TEXTURE_RED_FOX;
    }
  }  
}
