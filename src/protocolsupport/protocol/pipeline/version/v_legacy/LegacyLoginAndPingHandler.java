package protocolsupport.protocol.pipeline.version.v_legacy;

import java.util.Collections;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.LegacyServerPingResponseEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.Allocator;

@SuppressWarnings("deprecation")
@Sharable
public class LegacyLoginAndPingHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input)  {
		ProtocolSupportPacketDataSerializer serializer = new ProtocolSupportPacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_LEGACY);
		try {
			int packetId = input.readUnsignedByte();
			if (packetId == 0xFE) {
				writePing(ctx.channel(), serializer);
			} else if (packetId == 0x02) {
				writeLoginKick(serializer);
			} else {
				throw new DecoderException("Unknown packet id "+packetId + " in legacy login and ping handler");
			}
			ctx.channel().pipeline().firstContext().writeAndFlush(serializer).addListener(ChannelFutureListener.CLOSE);
			serializer = null;
		} finally {
			if (serializer != null) {
				serializer.release();
			}
		}
	}

	private static void writeLoginKick(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeByte(0xFF);
		serializer.writeString("Outdated client");
	}

	private static void writePing(Channel channel, ProtocolSupportPacketDataSerializer serializer) {
		Connection connection = ConnectionImpl.getFromChannel(channel);
		ServerListPingEvent bevent = new ServerListPingEvent(
			connection.getAddress().getAddress(),
			Bukkit.getMotd(), Bukkit.getOnlinePlayers().size(),
			Bukkit.getMaxPlayers()
		) {
			@Override
			public void setServerIcon(CachedServerIcon icon) {
			}
			@Override
			public Iterator<Player> iterator() {
				return Collections.emptyIterator();
			}
		};
		Bukkit.getPluginManager().callEvent(bevent);

		LegacyServerPingResponseEvent revent = new LegacyServerPingResponseEvent(connection, bevent.getMotd(), bevent.getMaxPlayers());
		Bukkit.getPluginManager().callEvent(revent);

		String response = ChatColor.stripColor(revent.getMotd())+"§"+bevent.getNumPlayers()+"§"+revent.getMaxPlayers();
		serializer.writeByte(0xFF);
		serializer.writeString(response);
	}

}
