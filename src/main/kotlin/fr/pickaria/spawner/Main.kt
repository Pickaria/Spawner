package fr.pickaria.spawner

import org.bukkit.plugin.java.JavaPlugin

internal class Main: JavaPlugin() {
	override fun onEnable() {
		super.onEnable()

		getCommand("spawner")?.setExecutor(Command())
		enableSpawnerLibrary()
	}
}

fun JavaPlugin.enableSpawnerLibrary() {
	server.pluginManager.registerEvents(Listeners(), this)
}