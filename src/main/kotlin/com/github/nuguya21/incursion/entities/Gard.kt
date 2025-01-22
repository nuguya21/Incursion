package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Incursion
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.WitherSkeleton
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class Gard: Monster {
    override val name: String = "gard"
    override val type: EntityType = EntityType.WITHER_SKELETON
    override val damage: Double = 12.0
    override val health: Double = 64.0
    override val equipment: EquipmentInfo = EquipmentInfo().apply {
        mainHand = ItemStack(Material.IRON_SWORD)
        chestplate = ItemStack(Material.LEATHER_CHESTPLATE).apply {
            this.itemMeta = (this.itemMeta as LeatherArmorMeta).apply { setColor(Color.BLACK) }
        }
        leggings = ItemStack(Material.LEATHER_LEGGINGS).apply {
            this.itemMeta = (this.itemMeta as LeatherArmorMeta).apply { setColor(Color.BLACK) }
        }
        boots = ItemStack(Material.LEATHER_BOOTS).apply {
            this.itemMeta = (this.itemMeta as LeatherArmorMeta).apply { setColor(Color.BLACK) }
        }
    }
    override val lootTable: LootTable = CustomLootTable(name)
    private val removeTime: Long = 20 * 10
    private lateinit var self: WitherSkeleton

    override fun spawn(location: Location): Entity {
        return super.spawn(location).apply {
            if (this is WitherSkeleton) {
                this.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, -1, 0))
                this.addPotionEffect(PotionEffect(PotionEffectType.SPEED, -1, 0))
                self = this
            }
        }
    }

    private val remover: BukkitRunnable = object : BukkitRunnable() {
        override fun run() {
            self.remove()
        }
    }

    override fun onTargeting(entity: Entity, target: Entity) {
        remover.cancel()
    }

    override fun onMissingTarget(entity: Entity) {
        remover.runTaskLater(Incursion.instance, removeTime)
    }
}