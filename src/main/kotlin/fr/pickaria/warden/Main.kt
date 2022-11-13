package fr.pickaria.warden

import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
	override fun onEnable() {
		super.onEnable()

		getCommand("warden")?.setExecutor(Command())

		logger.info("Plugin enabled")
	}
}