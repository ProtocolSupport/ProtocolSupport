package com.github.shevchik.protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.shevchik.protocolsupport.commands.CommandHandler;
import com.github.shevchik.protocolsupport.injector.NettyInjector;
import com.github.shevchik.protocolsupport.injector.ServerInjector;
import com.github.shevchik.protocolsupport.server.listeners.PlayerListener;
import com.github.shevchik.protocolsupport.utils.Allocator;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		try {
			Allocator.init();
			NettyInjector.inject();
			ServerInjector.inject();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
