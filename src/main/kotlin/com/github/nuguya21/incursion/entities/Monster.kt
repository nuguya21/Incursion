package com.github.nuguya21.incursion.entities

import com.github.nuguya21.incursion.Spawnable
import org.bukkit.Location
import org.bukkit.entity.Entity

interface Monster: Spawnable {
    override fun naturallySpawn(location: Location): Entity? {
        if (location.block.lightLevel > 0) return null
        return super.naturallySpawn(location)
    }
}