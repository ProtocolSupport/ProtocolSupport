package com.github.shevchik.protocolsupport.protocol.core.wrapped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import com.github.shevchik.protocolsupport.protocol.core.IPacketEncoder;

public class WrappedEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	private static final Logger logger = LogManager.getLogger();

	private IPacketEncoder realEncoder = new IPacketEncoder() {
		@Override
		public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		}
	};

	public void setRealEncoder(IPacketEncoder realEncoder) {
		this.realEncoder = realEncoder;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		try {
			realEncoder.encode(ctx, packet, output);
		} catch (Throwable t) {
			logger.error(t);
		}
	}

}
