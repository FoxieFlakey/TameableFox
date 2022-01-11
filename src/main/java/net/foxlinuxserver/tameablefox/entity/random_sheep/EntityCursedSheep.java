package net.foxlinuxserver.tameablefox.entity.random_sheep;

import net.foxlinuxserver.tameablefox.init.EntityInit;
import net.foxlinuxserver.tameablefox.util.Util;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityCursedSheep extends AnimalEntity implements IAnimatable {
  private final AnimationFactory ANIMATION_FACTORY = new AnimationFactory(this);
  
  public EntityCursedSheep(EntityType<? extends AnimalEntity> entityType, World world) {
    super(entityType, world);
  }
  
  // No child creation
  @Override
  public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
    return new EntityCursedSheep(EntityInit.ENTITY_CURSED_SHEEP, world);
  }
  
  @Override
  public void initGoals() {
    super.initGoals();
    
    // Goals
    this.goalSelector.add(1, new SwimGoal(this)); 
    this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4f));
    this.goalSelector.add(4, new MeleeAttackGoal(this, 1.f, true));
    this.goalSelector.add(4, new FleeEntityGoal<WolfEntity>(this, WolfEntity.class, 
                                                8.0f, 1.6, 1.4, 
                                                Util.IS_NOT_TAMED_PREDICATE));
    this.goalSelector.add(4, new FleeEntityGoal<PolarBearEntity>(this, 
                                                PolarBearEntity.class, 
                                                8.0f, 1.6, 1.4, 
                                                Util.ALWAYS_TRUE_LIVING_ENTITY_PREDICATE));
    this.goalSelector.add(6, new AnimalMateGoal(this, 1.f));
    this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.f));
    this.goalSelector.add(7, new WanderAroundGoal(this, 1.f));
    this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.f));
    this.goalSelector.add(10, new LookAroundGoal(this));
    
    // Targets
    this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
    this.targetSelector.add(7, new ActiveTargetGoal<AbstractSkeletonEntity>((MobEntity)this, AbstractSkeletonEntity.class, false));
  }
  
  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.isIn(ItemTags.FOX_FOOD); 
  }
  
  private <E extends IAnimatable> PlayState animationController(AnimationEvent<E> event) {
    if (this.navigation.isIdle()) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cursed_sheep.idle"));
    }
    return PlayState.CONTINUE;
  }
  
  @Override
  public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController<EntityCursedSheep>(this, "controller", 0, this::animationController));
  }
  
  @Override
  public AnimationFactory getFactory() {
    return ANIMATION_FACTORY;
  } 
}
