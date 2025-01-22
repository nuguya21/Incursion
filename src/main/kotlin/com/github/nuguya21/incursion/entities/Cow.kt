package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.loot.LootTable

class Cow: Spawnable {
    override val name: String = "cow"
    override val type: EntityType = EntityType.COW
    override val damage: Double = 0.0
    override val health: Double = 50.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.LEATHER, 1, 5, 20.0))
        addLoot(Loot(Material.BEEF, 1, 3, 10.0))
        addLoot(Loot(Material.BONE, 1, 3, 5.0))
    }

}