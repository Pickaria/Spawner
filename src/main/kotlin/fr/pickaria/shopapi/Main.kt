package fr.pickaria.shopapi

import org.bukkit.plugin.java.JavaPlugin

internal class Main: JavaPlugin() {
	override fun onEnable() {
		super.onEnable()

		getCommand("warden")?.setExecutor(Command())
		server.pluginManager.registerEvents(Listeners(), this)
	}
}