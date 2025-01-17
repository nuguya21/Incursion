package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.CustomLootTable.Loot
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Skeleton
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.Random

class DeepDarkSkeleton: Spawnable {
    override val name: String = "deep_dark_skeleton"
    override val type: EntityType = EntityType.SKELETON
    override val damage: Double = 10.0
    override val health: Double = 18.0
    override val equipment: EquipmentInfo = EquipmentInfo().apply {
        helmet = ItemStack(Material.LEATHER_HELMET)
        chestplate = ItemStack(Material.CHAINMAIL_CHESTPLATE)
        leggings = ItemStack(Material.LEATHER_LEGGINGS)
        boots = ItemStack(Material.GOLDEN_BOOTS)
        mainHand = ItemStack(Material.BOW)
    }
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.BONE, 1, 5, 30.0))
        addLoot(Loot(Material.BONE_MEAL, 1, 8, 50.0))
        addLoot(Loot(Material.BOW, 1, 1, 1.0))
        addLoot(Loot(Material.ARROW, 1, 5, 25.0))
    }

    override fun spawn(location: Location): Entity {
        return super.spawn(location).apply {
            if (this is Skeleton) {
                this.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, -1, 0))
            }
        }
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            if (Random().nextDouble() * 100 < 25) {
                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 2))
            }
        }
    }

}