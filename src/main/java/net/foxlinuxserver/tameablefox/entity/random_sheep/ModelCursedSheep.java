package net.foxlinuxserver.tameablefox.entity.random_sheep;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ModelCursedSheep extends AnimatedGeoModel<EntityCursedSheep> {
  private static final Identifier ANIMATION = new Identifier(TameableFox.MOD_ID, "animations/cursed_sheep.animation.json");
  private static final Identifier MODEL = new Identifier(TameableFox.MOD_ID, "geo/cursed_sheep.geo.json");
  private static final Identifier TEXTURE = new Identifier(TameableFox.MOD_ID, "textures/entity/cursed_sheep.png");
  
  @Override
  public Identifier getAnimationFileLocation(EntityCursedSheep entity) {
    return ANIMATION;
  }
  
  @Override
  public Identifier getModelLocation(EntityCursedSheep entity) {
    return MODEL;
  }
  
  @Override
  public Identifier getTextureLocation(EntityCursedSheep entity) {
    return TEXTURE;
  }  
}
