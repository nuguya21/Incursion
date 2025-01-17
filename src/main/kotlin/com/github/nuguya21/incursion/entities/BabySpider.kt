package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Spider
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class BabySpider: Spawnable {
    override val name: String = "baby_spider"
    override val type: EntityType = EntityType.SPIDER
    override val damage: Double = 1.0
    override val health: Double = 20.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.SPIDER_EYE, 1, 1, 10.0))
        addLoot(Loot(Material.STRING, 1 ,1, 0.05))
    }

    override fun spawn(location: Location): Entity {
        return super.spawn(location).apply {
            if (this is Spider) {
                this.getAttribute(Attribute.SCALE)?.baseValue = 0.5
                this.addPotionEffect(PotionEffect(PotionEffectType.SPEED, -1, 1))
            }
        }
    }

}