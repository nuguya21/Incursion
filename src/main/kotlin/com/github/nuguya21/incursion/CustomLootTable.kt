package com.github.nuguya21.incursion

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTable
import java.util.*

class CustomLootTable(private val name: String): LootTable {

    private val loots: MutableMap<Material, Loot> = mutableMapOf()

    fun addLoot(loot: Loot) {
        loots[loot.material] = loot
    }

    override fun getKey(): NamespacedKey {
        return NamespacedKey(Incursion.instance, "incursion/$name")
    }

    fun populateLoot(lootContext: LootContext): MutableCollection<ItemStack> {
        return populateLoot(Random(), lootContext)
    }

    override fun populateLoot(p0: Random?, p1: LootContext): MutableCollection<ItemStack> {
        val items = mutableSetOf<ItemStack>()
        for ((key, value) in loots) {
            if (p0!!.nextDouble() * 100 < value.probability) {
                items.add(ItemStack(key).apply {
                    amount = p0.nextInt(value.min, value.max + 1) + p0.nextInt(p1.lootingModifier)
                })
            }
        }
        return items
    }

    override fun fillInventory(p0: Inventory, p1: Random?, p2: LootContext) {
        p0.addItem(*populateLoot(p1, p2).toTypedArray())
    }

    class Loot(val material: Material, min: Int, max: Int, val probability: Double = 100.0) {
        val min: Int
        val max: Int

        init {
            if (min <= max) {
                this.min = min
                this.max = max
            } else {
                this.min = max
                this.max = min
            }
        }
    }
}