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

class DeepDarkZombie: Spawnable {
    override val name: String = "deep_dark_zombie"
    override val type: EntityType = EntityType.ZOMBIE
    override val damage: Double = 4.0
    override val health: Double = 18.0
    override val equipment: EquipmentInfo = EquipmentInfo().apply {
        chestplate = ItemStack(Material.DIAMOND_CHESTPLATE)
        leggings = ItemStack(Material.CHAINMAIL_LEGGINGS)
        boots = ItemStack(Material.IRON_BOOTS)
        mainHand = ItemStack(Material.STONE_HOE)
    }
    override val lootTable: LootTable = CustomLootTable(name).apply {
        addLoot(Loot(Material.ROTTEN_FLESH, 1, 5, 30.0))
        addLoot(Loot(Material.BONE, 1, 3, 15.0))
        addLoot(Loot(Material.POISONOUS_POTATO, 1, 1, 1.0))
        addLoot(Loot(Material.DIAMOND, 1, 1, 0.01))
    }

    override fun onAttack(entity: Entity, target: Entity) {
        if (target is LivingEntity) {
            target.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 5, 0))
            if (Random().nextDouble() * 100 < 20) {
                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 5, 1))
            }
        }
    }
}