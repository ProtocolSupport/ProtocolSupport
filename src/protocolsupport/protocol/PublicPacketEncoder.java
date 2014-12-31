package protocolsupport.protocol;

import net.minecraft.server.v1_8_R1.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface PublicPacketEncoder {

	public void publicEncode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception;

}
