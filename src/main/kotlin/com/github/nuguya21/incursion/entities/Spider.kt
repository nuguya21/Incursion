package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.loot.LootTable

class Spider: Spawnable {
    override val name: String = "spider"
    override val type: EntityType = EntityType.SPIDER
    override val damage: Double = 4.0
    override val health: Double = 8.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.SPIDER_EYE, 1, 3, 20.0))
        addLoot(Loot(Material.STRING, 1, 2, 0.05))
    }

}