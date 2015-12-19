package protocolsupport.protocol.core.initial;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import net.minecraft.server.v1_8_R3.MinecraftServer;

import protocolsupport.api.events.LegacyServerPingResponseEvent;
import protocolsupport.utils.Utils;

public class Ping11ResponseTask implements Runnable {

	private Channel channel;

	public Ping11ResponseTask(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			InetSocketAddress remoteAddress = (InetSocketAddress) Utils.getNetworkManagerSocketAddress(channel);
			ServerListPingEvent bevent = new ServerListPingEvent(
				remoteAddress.getAddress(),
				Bukkit.getMotd(), Bukkit.getOnlinePlayers().size(),
				Bukkit.getMaxPlayers()
			) {
				@Override
				public void setServerIcon(CachedServerIcon icon) {
				}
			};
			Bukkit.getPluginManager().callEvent(bevent);

			LegacyServerPingResponseEvent revent = new LegacyServerPingResponseEvent(remoteAddress, bevent.getMotd(), bevent.getMaxPlayers());
			Bukkit.getPluginManager().callEvent(revent);

			String response = ChatColor.stripColor(revent.getMotd())+"§"+bevent.getNumPlayers()+"§"+revent.getMaxPlayers();
			ByteBuf buf = Unpooled.buffer();
			buf.writeByte(255);
			buf.writeShort(response.length());
			buf.writeBytes(response.getBytes(StandardCharsets.UTF_16BE));
			channel.pipeline().firstContext().writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
		} catch (Throwable t) {
			if (MinecraftServer.getServer().isDebugging()) {
				t.printStackTrace();
			}
			channel.close();
		}
	}

}
