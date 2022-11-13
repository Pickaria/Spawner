package fr.pickaria.warden

import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
	override fun onEnable() {
		super.onEnable()

		getCommand("warden")?.setExecutor(Command())
		server.pluginManager.registerEvents(Listeners(), this)
	}
}