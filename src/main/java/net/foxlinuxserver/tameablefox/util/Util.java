package net.foxlinuxserver.tameablefox.util;

import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.predicate.entity.EntityPredicates;

public class Util {
  private Util() {}
  
  // Useful predicates
  public static final Predicate<LivingEntity> ALWAYS_TRUE_LIVING_ENTITY_PREDICATE = n -> true;
  public static final Predicate<LivingEntity> ALWAYS_FALSE_LIVING_ENTITY_PREDICATE = n -> false;
  public static final Predicate<Object> ALWAYS_TRUE = o -> true;
  public static final Predicate<Object> ALWAYS_FALSE = o -> false;
  
  public static final Predicate<LivingEntity> IS_TAMED_PREDICATE = entity -> {
    return ((TameableEntity) entity).isTamed();
  };
  public static final Predicate<LivingEntity> IS_NOT_TAMED_PREDICATE = 
                                                  Util.IS_TAMED_PREDICATE.negate();
  public static final Predicate<Entity> NOTICEABLE_PLAYER_FILTER = entity -> {
    return !entity.isSneaky() && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity);
  }; 
}
