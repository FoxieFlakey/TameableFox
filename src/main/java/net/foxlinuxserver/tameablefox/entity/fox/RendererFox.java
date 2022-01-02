package net.foxlinuxserver.tameablefox.entity.fox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class RendererFox extends GeoEntityRenderer<EntityFox> {
  public RendererFox(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelFox()); 
  } 
}
