package net.foxlinuxserver.tameablefox.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.foxlinuxserver.tameablefox.TameableFox;
import net.foxlinuxserver.tameablefox.entity.fox.EntityFox;
import net.foxlinuxserver.tameablefox.entity.fox.RendererFox;
import net.foxlinuxserver.tameablefox.entity.random_sheep.EntityCursedSheep;
import net.foxlinuxserver.tameablefox.entity.random_sheep.RendererCursedSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {
  private static final Logger LOGGER = LogManager.getLogger(EntityInit.class);
  private static List<EntityData<?>> entities = new ArrayList<>();
  private static Map<String, Boolean> entityExistence = new HashMap<>();
  
  private record EntityData<T extends Entity>(EntityType<T> entityType, 
                                              Runnable rendererRegisterer,
                                              DefaultAttributeContainer.Builder attribute, 
                                              String id) {}
  
  public static final EntityType<EntityFox> ENTITY_FOX = EntityInit.add("fox", 
               FoxEntity.createFoxAttributes(),
               () -> EntityRendererRegistry.register(EntityInit.ENTITY_FOX, RendererFox::new),
               FabricEntityTypeBuilder.<EntityFox>create()
                                      .spawnGroup(SpawnGroup.CREATURE)
                                      .dimensions(EntityDimensions.fixed(0.6f, 0.7f)) 
                                      .entityFactory(EntityFox::new)
                                      .build());
  
  public static final EntityType<EntityCursedSheep> ENTITY_CURSED_SHEEP = EntityInit.add("cursed_sheep", 
               FoxEntity.createFoxAttributes(),
               () -> EntityRendererRegistry.register(EntityInit.ENTITY_CURSED_SHEEP, RendererCursedSheep::new),
               FabricEntityTypeBuilder.<EntityCursedSheep>create()
                                      .spawnGroup(SpawnGroup.CREATURE)
                                      .dimensions(EntityDimensions.fixed(0.6f, 0.7f)) 
                                      .entityFactory(EntityCursedSheep::new)
                                      .build());
  
  public static <T extends Entity> EntityType<T> add(String name, 
                                                  DefaultAttributeContainer.Builder attribute, 
                                                  Runnable rendererRegisterer,
                                                  EntityType<T> entity) {
    if (entityExistence.get(name) != null)
      throw new IllegalArgumentException("Entity with id of '" + name + "' already exist"); 
    entities.add(new EntityData<>(entity, rendererRegisterer, attribute, name));
    
    return entity;
  }
  
  public static void register() {
    LOGGER.info("Registering entities");
    for (int i = 0; i < entities.size(); i++) {
      EntityData<?> entity = entities.get(i);
      Identifier id = new Identifier(TameableFox.MOD_ID, entity.id);
      
      // For registering entity default attributes
      
      // If entity.attribute is non null that mean it must be LivingEntity
      // otherwise if it not LivingEntity and have attribute its a bug or
      // if it LivingEntity but have no attribute mean its bug also
      if (entity.attribute != null) {
        try {
          @SuppressWarnings("unchecked")
          EntityType<? extends LivingEntity> tmp = (EntityType<? extends LivingEntity>) entity.entityType;
          FabricDefaultAttributeRegistry.register(tmp, entity.attribute);
        } catch (ClassCastException e) {
          LOGGER.error("Unexpected for entity '" + id.toString() + "'");
          throw new RuntimeException(e);
        }
      }
      Registry.register(Registry.ENTITY_TYPE, id, entity.entityType);
    }
    LOGGER.info("Done!");
  }
  
  @Environment(EnvType.CLIENT)
  public static void registerClient() {
    LOGGER.info("Registering entity renderer");
    for (EntityData<?> entity : entities) {
      entity.rendererRegisterer().run(); 
      //EntityRendererRegistry.register(entity.entityType, entity.entityRendererFactory);
    }
    LOGGER.info("Done!");
  }
}
