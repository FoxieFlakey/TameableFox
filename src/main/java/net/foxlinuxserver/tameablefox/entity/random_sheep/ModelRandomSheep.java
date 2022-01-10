package net.foxlinuxserver.tameablefox.entity.random_sheep;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ModelRandomSheep extends AnimatedGeoModel<EntityRandomSheep> {
  private static final Identifier ANIMATION = new Identifier(TameableFox.MOD_ID, "animations/random_sheep.animation.json");
  private static final Identifier MODEL = new Identifier(TameableFox.MOD_ID, "geo/random_sheep.geo.json");
  private static final Identifier TEXTURE = new Identifier(TameableFox.MOD_ID, "textures/entity/random_sheep.png");
  
  @Override
  public Identifier getAnimationFileLocation(EntityRandomSheep entity) {
    return ANIMATION;
  }
  
  @Override
  public Identifier getModelLocation(EntityRandomSheep entity) {
    return MODEL;
  }
  
  @Override
  public Identifier getTextureLocation(EntityRandomSheep entity) {
    return TEXTURE;
  }  
}
