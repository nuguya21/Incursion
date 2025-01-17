package com.github.nuguya21.incursion

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTables
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class Incursion : JavaPlugin() {

    companion object {
        lateinit var instance: Plugin
    }

    val spawner = Spawner()
    private val spawnables: MutableMap<String, Class<out Spawnable>> = mutableMapOf()
    /*
    private fun loadSpawnable(folder: File) {
        if (!folder.isDirectory) return
        val files = folder.listFiles() ?: return
        for (file in files) {
            val config = YamlConfiguration.loadConfiguration(file)
            config.name
            try {
                val creature = CustomEntity.fromConfig(file.nameWithoutExtension ,config)
                spawnables[creature.name] = creature
            } catch (e: EnumConstantNotPresentException) {
                logger.warning("'${config.name}' can not be spawnable. ")
            }
        }
    }
    */
    private fun loadSpawner(folder: File) {
        if (!folder.isDirectory) return
        val files = folder.listFiles() ?: return
        for (file in files) {
            val config = YamlConfiguration.loadConfiguration(file)
            for (name in config.getKeys(true)) {
                if (!spawnables.containsKey(name)) continue
                spawner.getSpawnData(file.nameWithoutExtension)[spawnables[name]!!] = config.getInt(name)
            }
        }
    }
    private fun loadSpawned() {
        val config = YamlConfiguration.loadConfiguration(getSpawendFile())
        for (key in config.getKeys(true)) {
            spawnables[config.getString(key)]?.let {
                spawner.spawned[UUID.fromString(key)] = it.getDeclaredConstructor().newInstance()
            }
        }
    }
    private fun saveSpawned() {
        val config = YamlConfiguration()
        for ((key, value) in spawner.spawned) {
            config.set(key.toString(), value.name)
        }
        config.save(getSpawendFile())
    }

    private fun dataFolder(): File {
        if (!dataFolder.exists()) dataFolder.mkdirs()
        return dataFolder
    }

    fun getSpawnableFolder(): File {
        val file = File(dataFolder(), "spawnable")
        if (file.mkdirs()) { // first time
            val exampleFile = File(file, "example")
            exampleFile.createNewFile()
            val config = YamlConfiguration.loadConfiguration(exampleFile)
            config.set("type", EntityType.ZOMBIE.name.lowercase())
            config.set("damage", 20.0)
            config.set("health", 100.0)
            config.createSection("equipment.helmet")
            config.set("equipment.helmet", ItemStack.of(Material.NETHERITE_HELMET))
            config.save(exampleFile)
        }
        return file
    }
    fun getSpawnerFolder(): File {
        val file = File(dataFolder(), "spawner")
        if (file.mkdirs()) { // first time
            val exampleFile = File(file, "plains")
            exampleFile.createNewFile()
            val config = YamlConfiguration.loadConfiguration(exampleFile)
            config.set("example", 1)
            config.save(exampleFile)
        }
        return file
    }
    fun getSpawendFile(): File {
        val file = File(dataFolder(), "spawned")
        if (!file.exists()) file.createNewFile()
        return file
    }

    override fun onEnable() {
        instance = this
        // loadSpawnable(getSpawnableFolder())
        loadSpawner(getSpawnerFolder())
        loadSpawned()

        Bukkit.getCommandMap().register("incursion", object : Command("reload") {
            override fun execute(p0: CommandSender, p1: String, p2: Array<out String>): Boolean {
                // loadSpawnable(getSpawnableFolder())
                loadSpawner(getSpawnerFolder())
                loadSpawned()
                return true
            }
        })

        Bukkit.getPluginManager().registerEvents(object: Listener {
            @EventHandler
            fun onSpawn(event: CreatureSpawnEvent) {
                if (event.spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL) {
                    if (spawner.spawn(event.location)) {
                        event.isCancelled = true
                    }
                }
            }
            @EventHandler
            fun onAttack(event: EntityDamageByEntityEvent) {
                spawner.spawned[event.entity.uniqueId]?.onDamage(event.entity, event.damager)
                spawner.spawned[event.damager.uniqueId]?.onAttack(event.damager, event.entity)
            }
            @EventHandler
            fun onDeath(event: EntityDeathEvent) {
                val entity = event.entity
                spawner.spawned[entity.uniqueId]?.let {
                    onDeath(event)
                    spawner.spawned.remove(entity.uniqueId)
                    val location = entity.location
                    val player = event.entity.killer
                    if (player != null) {
                        val lootingMod = player.inventory.itemInMainHand.getEnchantmentLevel(Enchantment.LOOTING)
                        val luckMod = player.getAttribute(Attribute.LUCK)!!.value
                        val builder = LootContext.Builder(location)

                        builder.lootedEntity(entity)
                        builder.lootingModifier(lootingMod)
                        builder.luck(luckMod.toFloat())
                        builder.killer(player)
                        val lootContext = builder.build()
                        LootTables.ZOMBIE.lootTable

                        val loot = it.lootTable
                        val drops = loot.populateLoot(Random(), lootContext)
                        event.drops.clear()
                        event.drops.addAll(drops)
                    }
                }


            }
            @EventHandler
            fun onTargeting(event: EntityTargetEvent) {
                val entity = event.entity
                val target = event.target
                if (target != null) { // targeting
                    spawner.spawned[entity.uniqueId]?.onTargeting(entity, target)
                } else { // missing target
                    spawner.spawned[entity.uniqueId]?.onMissingTarget(entity)
                }
            }
        }, this)
    }

    override fun onDisable() {
        saveSpawned()
    }

}
