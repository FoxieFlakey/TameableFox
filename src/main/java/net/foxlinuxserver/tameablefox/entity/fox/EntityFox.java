package net.foxlinuxserver.tameablefox.entity.fox;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
  private static final Logger LOGGER = LogManager.getLogger(EntityFox.class);
  
  private static final TrackedData<String> TRACKER_FOX_TYPE = 
                             DataTracker.registerData(EntityFox.class, 
                                                      TrackedDataHandlerRegistry.STRING);
  private static final TrackedData<Boolean> TRACKER_FOX_DERPY = 
                             DataTracker.registerData(EntityFox.class, 
                                                      TrackedDataHandlerRegistry.BOOLEAN);
  private static final TrackedData<Boolean> TRACKER_FOX_SLEEPING = 
                             DataTracker.registerData(EntityFox.class, 
                                                      TrackedDataHandlerRegistry.BOOLEAN);
  
  // Animations
  private static final AnimationBuilder ANIMATION_SLEEPING = new AnimationBuilder()
                                .addAnimation("animation.fox.sleep", true);
  private static final AnimationBuilder ANIMATION_NOT_SLEEPING = new AnimationBuilder()
                                .addAnimation("animation.fox.setup", true);
  private static final AnimationBuilder ANIMATION_WALK = new AnimationBuilder()
                                .addAnimation("animation.fox.walk", true);
  private static final AnimationBuilder ANIMATION_SIT = new AnimationBuilder()
                                .addAnimation("animation.fox.sit", true);
  private static final AnimationBuilder ANIMATION_BABY = new AnimationBuilder()
                                .addAnimation("animation.fox.baby", true);
  
  public enum FoxType {
    RED("red"),
    SNOW("snow");
    
    public final String value;
    private FoxType(String value) {this.value = value;}
    
    private static final Map<String, FoxType> lookup = new HashMap<>();
    
    static {
      for (FoxType t : FoxType.values()) 
        lookup.put(t.value, t); 
    }
    
    public static FoxType fromString(String str) {
      return lookup.getOrDefault(str, FoxType.RED);
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
  
  @Override
  public ActionResult interactMob(PlayerEntity player, Hand hand) { 
    ItemStack items = player.getStackInHand(hand);
    
    if (!this.isTamed()) {
      if (this.isTamingItem(items)) {
        // Tame if the fox havent tamed
        if (!player.getAbilities().creativeMode) {
          items.decrement(1);
        }
        
        this.setTamed(true);
        LOGGER.info("Tamed a fox!");
        return ActionResult.SUCCESS;
      } 
      
      return super.interactMob(player, hand); 
    } else {
      if (items.isFood() && this.getHealth() < this.getMaxHealth()) {
        // Try heal 
        if (!player.getAbilities().creativeMode) {
          items.decrement(1);
        }
        
        float prev = this.getHealth();
        this.heal(items.getItem().getFoodComponent().getHunger());
        LOGGER.info(String.format("Healed a tamed fox! (%f to %f)"), prev, this.getHealth());
        return ActionResult.SUCCESS;
      } else {
        // Set the tamed fox to sit 
        this.setSitting(!this.isSitting());
        LOGGER.info("Setting fox to " + this.isSitting());
        return ActionResult.SUCCESS;
      }
    }
  }
  
  public FoxType getFoxType() {
    return FoxType.fromString(this.dataTracker.get(TRACKER_FOX_TYPE));
  }
  
  public boolean isDerpy() {
    return this.dataTracker.get(TRACKER_FOX_DERPY);
  }
  
  @Override
  public boolean isSleeping() {
    return this.dataTracker.get(TRACKER_FOX_SLEEPING);
  }
  
  public void setSleeping(boolean val) {
    this.dataTracker.set(TRACKER_FOX_SLEEPING, val);
  }
  
  @Override
  public void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(TRACKER_FOX_TYPE, FoxType.RED.value);
    this.dataTracker.startTracking(TRACKER_FOX_DERPY, false);
    this.dataTracker.startTracking(TRACKER_FOX_SLEEPING, false);
  }
  
  @Override
  public void writeCustomDataToNbt(NbtCompound compound) {
    super.writeCustomDataToNbt(compound); 
    compound.putString("Type", this.dataTracker.get(TRACKER_FOX_TYPE));
    compound.putBoolean("Derpy", this.dataTracker.get(TRACKER_FOX_DERPY));
    compound.putBoolean("Sleeping", this.dataTracker.get(TRACKER_FOX_SLEEPING));
  }
  
  @Override
  public void readCustomDataFromNbt(NbtCompound compound) {
    super.readCustomDataFromNbt(compound); 
    this.dataTracker.set(TRACKER_FOX_TYPE, compound.getString("Type"));
    this.dataTracker.set(TRACKER_FOX_DERPY, compound.getBoolean("Derpy"));
    this.dataTracker.set(TRACKER_FOX_SLEEPING, compound.getBoolean("Sleeping"));
  }
  
  /*@Override
  public boolean isSitting() {
    return this.isInSittingPose();
  }
  
  @Override
  public void setSitting(boolean var) {
    this.setInSittingPose(var);
  }*/
  
  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.isIn(ItemTags.FOX_FOOD); 
  }
  
  public boolean isTamingItem(ItemStack stack) {
    return this.isBreedingItem(stack);
  }
  
  private <E extends IAnimatable> PlayState walkingAnimation(AnimationEvent<E> event) {
    if (event.isMoving()) {
      event.getController().setAnimation(ANIMATION_WALK);
      return PlayState.CONTINUE;
    } else {
      return PlayState.STOP;
    }
  }
  
  private <E extends IAnimatable> PlayState sleepingController(AnimationEvent<E> event) {
    if (this.isSleeping()) {
      event.getController().setAnimation(ANIMATION_SLEEPING);
      return PlayState.CONTINUE;
    } else {
      event.getController().setAnimation(ANIMATION_NOT_SLEEPING);
      return PlayState.CONTINUE;
    } 
  }
  
  private <E extends IAnimatable> PlayState sittingController(AnimationEvent<E> event) {
    if (this.isSitting()) {
      event.getController().setAnimation(ANIMATION_SIT);
      return PlayState.CONTINUE;
    } else {
      return PlayState.STOP;
    }
  }
  
  private <E extends IAnimatable> PlayState babyCheckController(AnimationEvent<E> event) {
    //event.getController().setAnimation(ANIMATION_BABY);
    return PlayState.CONTINUE;
  }
  
  @Override
  public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController<EntityFox>(this, "baby_check", 0, this::babyCheckController));
    data.addAnimationController(new AnimationController<EntityFox>(this, "sitting", 0, this::sittingController));
    data.addAnimationController(new AnimationController<EntityFox>(this, "sleeping", 0, this::sleepingController));
    data.addAnimationController(new AnimationController<EntityFox>(this, "walking", 0, this::walkingAnimation));
  }
  
  @Override
  public AnimationFactory getFactory() {
    return ANIMATION_FACTORY;
  } 
}
