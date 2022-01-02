// Made with Blockbench 4.1.1
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports

	package net.foxlinuxserver.tameablefox.entity.fox;
// Super broken right now

/*
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.CuboidBlockIterator;

public class ModelFox extends EntityModel<EntityFox> {
  private final ModelPart tail;
  private final ModelPart leg3;
  private final ModelPart leg2;
  private final ModelPart leg1;
  private final ModelPart leg0;
  private final ModelPart head;
  private final ModelPart body;
  private final ModelPart head_sleeping;
  private final int textureWidth;
  private final int textureHeight;
  
  // Attempt to reimplement
  private static class ModelPart {
    public ModelPart(EntityModel<EntityFox> entity) {
      
    }
  }
  
  public ModelFox() {
    textureWidth = 64;
    textureHeight = 32;
    tail = new ModelPart(this);
    tail.setPivot(0.0F, 17.0F, 6.0F);
    setRotationAngle(tail, 1.5708F, 0.0F, 0.0F);
    tail.setTextureOffset(28, 0).addCuboid(-2.0F, 2.0F, -3.25F, 4.0F, 9.0F, 5.0F, 0.0F, false);
    
    leg3 = new ModelPart(this);
    leg3.setPivot(1.0F, 18.0F, -1.0F);
    leg3.setTextureOffset(22, 24).addCuboid(0.005F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
    
    leg2 = new ModelPart(this);
    leg2.setPivot(-3.0F, 18.0F, -1.0F);
    leg2.setTextureOffset(14, 24).addCuboid(-0.005F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
    
    leg1 = new ModelPart(this);
    leg1.setPivot(1.0F, 18.0F, 6.0F);
    leg1.setTextureOffset(22, 24).addCuboid(0.005F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
    
    leg0 = new ModelPart(this);
    leg0.setPivot(-3.0F, 18.0F, 6.0F);
    leg0.setTextureOffset(14, 24).addCuboid(-0.005F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
    
    head = new ModelPart(this);
    head.setPivot(0.0F, 16.0F, -3.0F);
    head.setTextureOffset(0, 0).addCuboid(-4.0F, -2.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, false);
    head.setTextureOffset(0, 0).addCuboid(-4.0F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(22, 0).addCuboid(2.0F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    head.setTextureOffset(0, 24).addCuboid(-2.0F, 2.0F, -9.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
    
    body = new ModelPart(this);
    body.setPivot(0.0F, 17.5F, 3.0F);
    setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
    body.setTextureOffset(30, 15).addCuboid(-3.0F, -6.0F, -3.0F, 6.0F, 11.0F, 6.0F, 0.0F, false);
    
    head_sleeping = new ModelPart(this);
    head_sleeping.setPivot(0.0F, 16.0F, -3.0F);
    head_sleeping.setTextureOffset(0, 12).addCuboid(-4.0F, -2.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, false);
    head_sleeping.setTextureOffset(0, 0).addCuboid(-4.0F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    head_sleeping.setTextureOffset(22, 0).addCuboid(2.0F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    head_sleeping.setTextureOffset(0, 24).addCuboid(-2.0F, 2.0F, -9.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
  }
  
  @Override
  public void setAngles(EntityFox entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
    //previously the render function, render code was moved to a method below
  }
  
  @Override
  public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
    tail.render(matrixStack, buffer, packedLight, packedOverlay);
    leg3.render(matrixStack, buffer, packedLight, packedOverlay);
    leg2.render(matrixStack, buffer, packedLight, packedOverlay);
    leg1.render(matrixStack, buffer, packedLight, packedOverlay);
    leg0.render(matrixStack, buffer, packedLight, packedOverlay);
    head.render(matrixStack, buffer, packedLight, packedOverlay);
    body.render(matrixStack, buffer, packedLight, packedOverlay);
    head_sleeping.render(matrixStack, buffer, packedLight, packedOverlay);
  }
  
  public void setRotationAngle(ModelPart bone, float x, float y, float z) {
    bone.pitch = x;
    bone.yaw = y;
    bone.roll = z;
  } 
}

*/
