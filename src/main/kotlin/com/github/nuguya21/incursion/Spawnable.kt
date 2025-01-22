package com.github.nuguya21.incursion

import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.loot.LootTable

interface Spawnable {
    val name: String
    val type: EntityType?
    val damage: Double
    val health: Double
    val equipment: EquipmentInfo
    val lootTable: LootTable
    fun naturallySpawn(location: Location): Entity? {
        return spawn(location)
    }
    fun spawn(location: Location): Entity {
        return location.world.spawnEntity(location, type ?: EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM) {
            if (it is LivingEntity) {
                it.equipment?.helmet = equipment.helmet
                it.equipment?.chestplate = equipment.chestplate
                it.equipment?.leggings = equipment.leggings
                it.equipment?.boots = equipment.boots
                it.equipment?.setItemInMainHand(equipment.mainHand)
                it.equipment?.setItemInOffHand(equipment.offHand)
                it.getAttribute(Attribute.MAX_HEALTH)?.baseValue = health
                it.health = health
                it.getAttribute(Attribute.ATTACK_DAMAGE)?.baseValue = damage * 2
            }
        }
    }
    fun onDeath(event: EntityDeathEvent) {}
    fun onTargeting(entity: Entity, target: Entity) {}
    fun onMissingTarget(entity: Entity) {}
    fun onTick(entity: Entity, target: Entity) {}
    fun onAttack(entity: Entity, target: Entity) {}
    fun onDamage(entity: Entity, damager: Entity) {}
}