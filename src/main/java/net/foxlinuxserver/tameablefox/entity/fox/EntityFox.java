package net.foxlinuxserver.tameablefox.entity.fox;

import net.foxlinuxserver.tameablefox.init.EntityInit;
import net.foxlinuxserver.tameablefox.util.Util;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.ai.goal.UntamedActiveTargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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

public class EntityFox extends TameableEntity implements IAnimatable {
  private final AnimationFactory ANIMATION_FACTORY = new AnimationFactory(this);
  
  private static final TrackedData<Integer> TRACKER_FOX_TYPE = 
                             DataTracker.registerData(EntityFox.class, 
                                                      TrackedDataHandlerRegistry.INTEGER);
  private static final TrackedData<Boolean> TRACKER_FOX_DERPY = 
                             DataTracker.registerData(EntityFox.class, 
                                                      TrackedDataHandlerRegistry.BOOLEAN);
  
  public enum FoxType {
    RED(FoxType.RED_VALUE),
    ARCTIC(FoxType.ARCTIC_VALUE);
    
    public static final int RED_VALUE = 1;
    public static final int ARCTIC_VALUE = 2;
    
    public final int value;
    private FoxType(int value) {this.value = value;}
    
    public static FoxType fromInteger(int integer) {
      switch (integer) {
        case FoxType.RED_VALUE:             return FoxType.RED;
        case FoxType.ARCTIC_VALUE:          return FoxType.ARCTIC;
        default:                            return FoxType.RED; 
      } 
    }
  } 
  
  public EntityFox(EntityType<? extends TameableEntity> entityType, World world) {
    super(entityType, world);
  }
  
  // No child creation
  @Override
  public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
    return new EntityFox(EntityInit.ENTITY_FOX, world);
  }
  
  @Override
  public void initGoals() {
    super.initGoals();
    
    // Goals
    this.goalSelector.add(1, new SwimGoal(this));
    this.goalSelector.add(2, new SitGoal(this));
    this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4f));
    this.goalSelector.add(4, new MeleeAttackGoal(this, 1.f, true));
    this.goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 
                                    16.0f, 1.6, 1.4, 
                                    entity -> {
                                      return !this.isOwner(entity) && 
                                             Util.NOTICEABLE_PLAYER_FILTER.test(entity);
                                    }));
    this.goalSelector.add(4, new FleeEntityGoal<WolfEntity>(this, WolfEntity.class, 
                                                8.0f, 1.6, 1.4, 
                                                Util.IS_NOT_TAMED_PREDICATE));
    this.goalSelector.add(4, new FleeEntityGoal<PolarBearEntity>(this, 
                                                PolarBearEntity.class, 
                                                8.0f, 1.6, 1.4, 
                                                Util.ALWAYS_TRUE_LIVING_ENTITY_PREDICATE));
    this.goalSelector.add(5, new FollowOwnerGoal(this, 1.f, 10.f, 2.f, false));
    this.goalSelector.add(6, new AnimalMateGoal(this, 1.f));
    this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.f));
    this.goalSelector.add(7, new WanderAroundGoal(this, 1.f));
    this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.f));
    this.goalSelector.add(10, new LookAroundGoal(this));
    
    // Targets
    this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
    this.targetSelector.add(2, new AttackWithOwnerGoal(this));
    this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
    this.targetSelector.add(6, new UntamedActiveTargetGoal<TurtleEntity>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    this.targetSelector.add(7, new ActiveTargetGoal<AbstractSkeletonEntity>((MobEntity)this, AbstractSkeletonEntity.class, false));
  }
  
  public FoxType getFoxType() {
    return FoxType.fromInteger(this.dataTracker.get(TRACKER_FOX_TYPE));
  }
  
  public boolean isDerpy() {
    return this.dataTracker.get(TRACKER_FOX_DERPY);
  }
  
  @Override
  public void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(TRACKER_FOX_TYPE, FoxType.RED.value);
    this.dataTracker.startTracking(TRACKER_FOX_DERPY, false);
  }
  
  @Override
  public void writeCustomDataToNbt(NbtCompound compound) {
    super.writeCustomDataToNbt(compound); 
    compound.putInt("Type", this.dataTracker.get(TRACKER_FOX_TYPE));
    compound.putBoolean("Derpy", this.dataTracker.get(TRACKER_FOX_DERPY));
  }
  
  @Override
  public void readCustomDataFromNbt(NbtCompound compound) {
    super.readCustomDataFromNbt(compound); 
    this.dataTracker.set(TRACKER_FOX_TYPE, compound.getInt("Type"));
    this.dataTracker.set(TRACKER_FOX_DERPY, compound.getBoolean("Derpy"));
  }
  
  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.isIn(ItemTags.FOX_FOOD); 
  }
  
  private <E extends IAnimatable> PlayState animationController(AnimationEvent<E> event) {
    if (this.navigation.isIdle()) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fox.idle"));
    }
    return PlayState.CONTINUE;
  }
  
  @Override
  public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController<EntityFox>(this, "controller", 0, this::animationController));
  }
  
  @Override
  public AnimationFactory getFactory() {
    return ANIMATION_FACTORY;
  } 
}
