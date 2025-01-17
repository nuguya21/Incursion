package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class Skeleton: Spawnable {
    override val name: String = "skeleton"
    override val type: EntityType = EntityType.SKELETON
    override val damage: Double = 5.0
    override val health: Double = 10.0
    override val equipment: EquipmentInfo = EquipmentInfo().apply {
        mainHand = ItemStack(Material.BOW)
    }
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.BONE, 1, 3, 30.0))
        addLoot(Loot(Material.BONE_MEAL, 1, 5, 50.0))
        addLoot(Loot(Material.BOW, 1, 1, 1.0))
        addLoot(Loot(Material.ARROW, 1, 3, 25.0))
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            if (Random().nextDouble() * 100 < 15) {
                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 1))
            }
        }
    }
}