package com.github.nuguya21.incursion

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.EntityType
import org.bukkit.loot.LootTable

class CustomEntity(
    override val name: String,
    override val type: EntityType?,
    override val damage: Double = 1.0,
    override val health: Double = 1.0
    ): Spawnable
{

    companion object{
        @Throws(EnumConstantNotPresentException::class)
        fun fromConfig(name: String, config: YamlConfiguration): CustomEntity {
            val creature = CustomEntity(
                name,
                config.getString("type").let {
                    if (it == null) null
                    else EntityType.valueOf(it.uppercase())
                },
                config.getDouble("damage"),
                config.getDouble("health")
            )
            creature.equipment.helmet = config.getItemStack("equipment.helmet")
            creature.equipment.chestplate = config.getItemStack("equipment.chestplate")
            creature.equipment.leggings = config.getItemStack("equipment.leggings")
            creature.equipment.boots = config.getItemStack("equipment.boots")
            creature.equipment.mainHand = config.getItemStack("equipment.mainHand")
            creature.equipment.offHand = config.getItemStack("equipment.offHand")
            return creature
        }
    }

    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable("incursion/${name}")
}