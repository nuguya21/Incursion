package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import com.github.nuguya21.incursion.CustomLootTable.Loot
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*


class Zombie: Monster {
    override val name: String = "zombie"
    override val type: EntityType = EntityType.ZOMBIE
    override val damage: Double = 2.0
    override val health: Double = 12.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.ROTTEN_FLESH, 1, 3, 30.0))
        addLoot(Loot(Material.BONE, 1, 2, 15.0))
        addLoot(Loot(Material.POISONOUS_POTATO, 1, 1, 1.0))
        addLoot(Loot(Material.IRON_INGOT, 1, 1, 0.01))
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            val random = Random()
            if (random.nextBoolean()) {
                target.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 5, 0))
            }
            if (random.nextDouble() * 100 < 15) {
                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 3, 0))
            }
        }
    }
}