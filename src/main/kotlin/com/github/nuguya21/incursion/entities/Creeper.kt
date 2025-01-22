package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Creeper
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.loot.LootTable

class Creeper: Monster {
    override val name: String = "creeper"
    override val type: EntityType = EntityType.CREEPER
    override val damage: Double = 10.0
    override val health: Double = 6.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.GUNPOWDER, 1, 2, 15.0))
    }

    override fun spawn(location: Location): Entity {
        return super.spawn(location).apply {
            if (this is Creeper) {
                fireTicks = -30
            }
        }
    }

    override fun onMissingTarget(entity: Entity) {
        if (entity is Creeper) {
            entity.fireTicks = -30
        }
    }
}