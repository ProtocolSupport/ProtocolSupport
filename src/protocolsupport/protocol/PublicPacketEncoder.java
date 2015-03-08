package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;

public interface PublicPacketEncoder {

	public void publicEncode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception;

}
