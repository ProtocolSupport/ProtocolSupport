package protocolsupport.protocol.packet.v_legacy;

import java.net.InetSocketAddress;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.LegacyServerPingResponseEvent;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.serializer.RecyclableProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

@SuppressWarnings("deprecation")
@Sharable
public class LegacyLoginAndPingHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input) throws Exception {
		RecyclableProtocolSupportPacketDataSerializer serializer = RecyclableProtocolSupportPacketDataSerializer.create(ProtocolVersion.MINECRAFT_LEGACY);
		try {
			int packetId = input.readUnsignedByte();
			if (packetId == 0xFE) {
				writePing((InetSocketAddress) ChannelUtils.getNetworkManagerSocketAddress(ctx.channel()), serializer);
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

	private static void writePing(InetSocketAddress remoteAddress, ProtocolSupportPacketDataSerializer serializer) {
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
		serializer.writeByte(0xFF);
		serializer.writeString(response);
	}

}
