package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.injector.NettyInjector;
import protocolsupport.injector.ServerInjector;

public class ProtocolSupport extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		try {
			NettyInjector.inject();
			ServerInjector.inject();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("protocolsupport").setExecutor(new CommandHandler());
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
