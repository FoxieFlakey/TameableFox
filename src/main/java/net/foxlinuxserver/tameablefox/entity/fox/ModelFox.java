package net.foxlinuxserver.tameablefox.entity.fox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ModelFox extends AnimatedGeoModel<EntityFox> {
  @Override
	public Identifier getAnimationFileLocation(EntityFox entity) {
	  return new Identifier(TameableFox.MOD_ID, "animations/fox.animation.json");
	}
  
	@Override
	public Identifier getModelLocation(EntityFox entity) {
 	  return new Identifier(TameableFox.MOD_ID, "geo/fox.geo.json");
	}
  
	@Override
	public Identifier getTextureLocation(EntityFox entity) {
	  return entity.foxType == EntityFox.FoxType.RED ? 
                          new Identifier(TameableFox.MOD_ID, "textures/entity/fox.png") :
                          new Identifier(TameableFox.MOD_ID, "textures/entity/arctic_fox.png"); 
	} 
}
