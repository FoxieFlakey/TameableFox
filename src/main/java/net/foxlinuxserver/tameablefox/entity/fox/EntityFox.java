package net.foxlinuxserver.tameablefox.entity.fox;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityFox extends TameableEntity implements IAnimatable {
  private final AnimationFactory ANIMATION_FACTORY = new AnimationFactory(this);
  protected FoxType foxType;
  
  public enum FoxType {
    RED,
    ARCTIC
  } 
  
  public EntityFox(EntityType<? extends TameableEntity> entityType, World world) {
    super(entityType, world);
  }
  
  // No child creation
  @Override
  public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
    return null;
  }
  
  @Override
  public void initGoals() {
    //this.followChickenAndRabbitGoal = new ActiveTargetGoal<AnimalEntity>(this, AnimalEntity.class, 10, false, false, entity -> entity instanceof ChickenEntity || entity instanceof RabbitEntity);
    //this.followBabyTurtleGoal = new ActiveTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, false, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER);
    //this.followFishGoal = new ActiveTargetGoal<FishEntity>(this, FishEntity.class, 20, false, false, entity -> entity instanceof SchoolingFishEntity);
    //this.goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 16.0f, 1.6, 1.4, entity -> NOTICEABLE_PLAYER_FILTER.test((Entity)entity) && !this.canTrust(entity.getUuid()) && !this.isAggressive()));
    //this.goalSelector.add(4, new FleeEntityGoal<WolfEntity>(this, WolfEntity.class, 8.0f, 1.6, 1.4, entity -> !((WolfEntity)entity).isTamed() && !this.isAggressive()));
    //this.goalSelector.add(4, new FleeEntityGoal<PolarBearEntity>(this, PolarBearEntity.class, 8.0f, 1.6, 1.4, entity -> !this.isAggressive()));
    //this.goalSelector.add(6, new JumpChasingGoal());
    //this.goalSelector.add(10, new EatSweetBerriesGoal((double)1.2f, 12, 1));
    super.initGoals();
    
    this.goalSelector.add(8, new FollowParentGoal(this, 1.25));
    this.goalSelector.add(10, new PounceAtTargetGoal(this, 0.4f));
    this.goalSelector.add(11, new WanderAroundFarGoal(this, 1.0));
    this.goalSelector.add(12, new LookAtEntityGoal(this, PlayerEntity.class, 24.0f));
  }
  
  @Override
  public void initDataTracker() {
    super.initDataTracker();
  }
  
  private <E extends IAnimatable> PlayState canAnimate(AnimationEvent<E> event) {
	 return PlayState.CONTINUE;
	}
  
  @Override
  public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController<EntityFox>(this, "controller", 0, this::canAnimate));
  }
  
  @Override
  public AnimationFactory getFactory() {
    return ANIMATION_FACTORY;
  } 
}
