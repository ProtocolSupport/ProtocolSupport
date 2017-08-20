package protocolsupport.protocol.pipeline.version.v_legacy;

import org.bukkit.ChatColor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.netty.Allocator;

@Sharable
public class LegacyLoginAndPingHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input)  {
		int packetId = input.readUnsignedByte();
		if (packetId == 0xFE) {
			writePing(ctx.channel());
		} else if (packetId == 0x02) {
			writeLoginKick(ctx.channel());
		} else {
			throw new DecoderException("Unknown packet id "+packetId + " in legacy login and ping handler");
		}
	}

	private static void writeLoginKick(Channel channel) {
		ByteBuf buf = Allocator.allocateBuffer();
		try {
			buf.writeByte(0xFF);
			StringSerializer.writeString(buf, ProtocolVersion.getOldest(ProtocolType.PC), "Outdated client");
			channel.pipeline().firstContext().writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
			buf = null;
		} finally {
			if (buf != null) {
				buf.release();
			}
		}
	}

	private static void writePing(Channel channel) {
		AbstractStatusListener.executeTask(() -> {
			ServerPingResponseEvent revent = AbstractStatusListener.createResponse(channel);
			ByteBuf buf = Allocator.allocateBuffer();
			try {
				buf.writeByte(0xFF);
				StringSerializer.writeString(buf, ProtocolVersion.getOldest(ProtocolType.PC), ChatColor.stripColor(revent.getMotd())+"§"+revent.getPlayers().size()+"§"+revent.getMaxPlayers());
				ChannelHandlerContext first = channel.pipeline().firstContext();
				if (first != null) {
					first.writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE);
					buf = null;
				}
			} finally {
				if (buf != null) {
					buf.release();
				}
			}
		});
	}

}
