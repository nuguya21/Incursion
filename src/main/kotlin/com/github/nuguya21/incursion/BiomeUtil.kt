package com.github.nuguya21.incursion

import org.bukkit.block.Biome

class BiomeUtil {
    companion object {
        fun overworld(): Collection<Biome> {
            return listOf(
                Biome.BADLANDS,
                Biome.BAMBOO_JUNGLE,
                Biome.BEACH,
                Biome.BIRCH_FOREST,
                Biome.COLD_OCEAN,
                Biome.DARK_FOREST,
                Biome.DEEP_COLD_OCEAN,
                Biome.DEEP_FROZEN_OCEAN,
                Biome.DEEP_LUKEWARM_OCEAN,
                Biome.DEEP_OCEAN,
                Biome.DESERT,
                Biome.DRIPSTONE_CAVES,
                Biome.FOREST,
                Biome.FROZEN_OCEAN,
                Biome.FROZEN_PEAKS,
                Biome.FROZEN_RIVER,
                Biome.GROVE,
                Biome.ICE_SPIKES,
                Biome.JAGGED_PEAKS,
                Biome.JUNGLE,
                Biome.LUKEWARM_OCEAN,
                Biome.LUSH_CAVES,
                Biome.MANGROVE_SWAMP,
                Biome.MEADOW,
                Biome.MUSHROOM_FIELDS,
                Biome.OCEAN,
                Biome.PLAINS,
                Biome.RIVER,
                Biome.SAVANNA,
                Biome.SAVANNA_PLATEAU,
                Biome.SNOWY_BEACH,
                Biome.SNOWY_PLAINS,
                Biome.SNOWY_SLOPES,
                Biome.SNOWY_TAIGA,
                Biome.SPARSE_JUNGLE,
                Biome.STONY_PEAKS,
                Biome.STONY_SHORE,
                Biome.SUNFLOWER_PLAINS,
                Biome.SWAMP,
                Biome.TAIGA,
                Biome.WINDSWEPT_FOREST,
                Biome.WINDSWEPT_GRAVELLY_HILLS,
                Biome.WINDSWEPT_HILLS,
                Biome.WINDSWEPT_SAVANNA,
                Biome.WOODED_BADLANDS
            )
        }

        fun nether(): Collection<Biome> {
            return listOf(
                Biome.NETHER_WASTES,
                Biome.BASALT_DELTAS,
                Biome.CRIMSON_FOREST,
                Biome.SOUL_SAND_VALLEY,
                Biome.WARPED_FOREST
            )
        }

        fun end(): Collection<Biome> {
            return listOf(
                Biome.THE_END,
                Biome.END_BARRENS,
                Biome.END_HIGHLANDS,
                Biome.END_MIDLANDS,
                Biome.SMALL_END_ISLANDS
            )
        }
    }
}
