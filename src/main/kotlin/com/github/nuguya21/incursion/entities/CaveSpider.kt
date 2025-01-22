package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class CaveSpider: Monster {
    override val name: String = "cave_spider"
    override val type: EntityType = EntityType.CAVE_SPIDER
    override val damage: Double = 1.0
    override val health: Double = 20.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.SPIDER_EYE, 1, 2, 15.0))
        addLoot(Loot(Material.STRING, 1, 3, 0.05))
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 7, 0))
        }
    }
}