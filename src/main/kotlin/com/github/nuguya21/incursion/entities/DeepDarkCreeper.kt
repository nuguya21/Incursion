package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Incursion
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.loot.LootTable
import org.bukkit.scheduler.BukkitRunnable

class DeepDarkCreeper: Monster {
    override val name: String = "deep_dark_creeper"
    override val type: EntityType = EntityType.CREEPER
    override val damage: Double = 80.0
    override val health: Double = 8.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.GUNPOWDER, 1, 3, 20.0))
    }

    override fun onDeath(event: EntityDeathEvent) {
        val entity = event.entity
        object : BukkitRunnable() {
            var i = 0
            override fun run() {
                if (i >= 20) {
                    entity.location.createExplosion(entity, 3f, false)
                }
                i++
            }
        }.runTaskTimer(Incursion.instance, 0L, 1L)

    }

}