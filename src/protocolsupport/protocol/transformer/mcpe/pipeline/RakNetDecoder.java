package protocolsupport.protocol.transformer.mcpe.pipeline;

import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

public class RakNetDecoder extends MessageToMessageDecoder<DatagramPacket> {

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> obj) throws Exception {
		ByteBuf buf = packet.content();
		RakNetPacket rpacket = new RakNetPacket(packet.sender());
		rpacket.decode(buf);
		obj.add(rpacket);
	}

}
