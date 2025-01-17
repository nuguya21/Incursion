package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.Random

class DeepDarkSpider: Spawnable {
    override val name: String = "deep_dark_spider"
    override val type: EntityType = EntityType.SPIDER
    override val damage: Double = 6.0
    override val health: Double = 18.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.SPIDER_EYE, 1, 5, 15.0))
        addLoot(Loot(Material.STRING, 1, 5, 0.05))
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            val random = Random()
            if (random.nextDouble() * 100 < 25) {
                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 1))

            }
            if (random.nextDouble() * 100 < 10) {
                target.addPotionEffect(PotionEffect(PotionEffectType.WITHER, 5, 1))
            }
        }
    }
}