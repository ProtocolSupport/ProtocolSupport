package protocolsupport.protocol.v_1_7.clientboundtransformer;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public interface PacketTransformer {

	public void tranform(ChannelHandlerContext ctx, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException;

}
