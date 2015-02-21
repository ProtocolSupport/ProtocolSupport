package protocolsupport.protocol.initial;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.ServerListPingEvent;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

public class OldPingResponseTask implements Runnable {

	private InitialPacketDecoder initialDecoder;
	private Channel channel;

	public OldPingResponseTask(InitialPacketDecoder initialDecoder, Channel channel) {
		this.initialDecoder = initialDecoder;
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			SocketAddress remoteAddress = channel.remoteAddress();
			if (channel.isOpen() && !initialDecoder.protocolSet) {
				ServerListPingEvent event = new ServerListPingEvent(
					((InetSocketAddress) remoteAddress).getAddress(),
					Bukkit.getMotd(), Bukkit.getOnlinePlayers().size(),
					Bukkit.getMaxPlayers()
				);
				Bukkit.getPluginManager().callEvent(event);
				String response = ChatColor.stripColor(event.getMotd())+"§"+event.getNumPlayers()+"§"+event.getMaxPlayers();
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(255);
				buf.writeShort(response.length());
				buf.writeBytes(response.getBytes(StandardCharsets.UTF_16BE));
				channel.pipeline().firstContext().writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
			}
		} catch (Throwable t) {
			if (channel.isOpen()) {
				channel.close();
			}
		}
	}

}
