package net.foxlinuxserver.tameablefox.entity.random_sheep;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class RendererCursedSheep extends GeoEntityRenderer<EntityCursedSheep> {
  public RendererCursedSheep(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelCursedSheep()); 
  } 
}
