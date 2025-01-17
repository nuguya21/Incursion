package com.github.nuguya21.incursion

import org.bukkit.Location
import java.util.*
import kotlin.random.Random

class Spawner {

    private val data: MutableMap<String, MutableMap<Class<out Spawnable>, Int>> = mutableMapOf()
    val spawned: MutableMap<UUID, Spawnable> = mutableMapOf()

    fun getSpawnData(biome: String): MutableMap<Class<out Spawnable>, Int> {
        if (!this.data.containsKey(biome)) this.data[biome] = mutableMapOf()
        return this.data[biome]!!
    }

    fun isSpawning(biome: String): Boolean {
        return data.containsKey(biome)
    }

    @Suppress("UnstableApiUsage", "removal")
    fun spawn(location: Location): Boolean {
        val biome = location.block.biome
        val name = biome.name().lowercase()
        if (isSpawning(name)) {
            if (getSpawnData(name).isEmpty()) return false
            val data = getSpawnData(name)
            var temp = 0
            val random = Random.nextDouble()
            for ((key, value) in data) {
                temp += value
                if (random < temp) {
                    val spawnable = key.getDeclaredConstructor().newInstance()
                    val entity = spawnable.spawn(location)
                    spawned[entity.uniqueId] = spawnable
                }
            }
            return true
        } else return false
    }
}