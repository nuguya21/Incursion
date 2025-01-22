package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.CustomLootTable
import com.github.nuguya21.incursion.EquipmentInfo
import com.github.nuguya21.incursion.Incursion
import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Warden
import org.bukkit.loot.LootTable
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.*

class Warden: Monster {
    override val name: String = "warden"
    override val type: EntityType = EntityType.WARDEN
    override val damage: Double = 32.0
    override val health: Double = 1024.0
    override val equipment: EquipmentInfo = EquipmentInfo()
    override val lootTable: LootTable = CustomLootTable(name)
    private lateinit var self: Warden

    val gards: MutableCollection<UUID> = mutableSetOf()

    override fun spawn(location: Location): Entity {
        return super.spawn(location).apply {
            if (this is Warden) {
                self = this
            }
        }
    }

    val gardSpawn = object : BukkitRunnable() {
        var token = 0
        var i = 0
        override fun run() {
            if (i >= gardSpawnDelay) token += 2 // 한 번에 2마리
            if (token > 0) {
                if (self.isOnGround) {
                    val poses: List<Vector> = mutableListOf(
                        Vector(1, 0, 0),
                        Vector(-1, 0, 0),
                        Vector(0, 0, 1),
                        Vector(0, 0, -1)
                    ).apply { shuffle() }
                    for (pos in poses) {
                        val loc = self.location.clone().add(pos)
                        if (!loc.clone().add(0.0, -1.0, 0.0).block.type.isSolid) continue
                        if (loc.clone().add(0.0, 1.0, 0.0).block.type.isSolid) continue
                        if (loc.clone().add(0.0, 2.0, 0.0).block.type.isSolid) continue
                        Incursion.getSpawner().spawn(loc, Gard::class.java)
                        loc.world.playSound(loc, Sound.BLOCK_BREWING_STAND_BREW, 1f, 1f)
                        loc.world.spawnParticle(Particle.ENTITY_EFFECT, loc, 10, 1.0, 1.0, 1.0, Color.BLACK)
                        token--
                    }
                }
            }
            i++
        }
    }
    val gardSpawnDelay: Long = 20 * 60

    override fun onTargeting(entity: Entity, target: Entity) {
        gardSpawn.runTaskTimer(Incursion.instance, 0L, gardSpawnDelay)
    }

    override fun onMissingTarget(entity: Entity) {
        gardSpawn.cancel()
    }
}