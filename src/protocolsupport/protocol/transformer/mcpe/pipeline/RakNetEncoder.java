package protocolsupport.protocol.transformer.mcpe.pipeline;

import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetPacket;
import protocolsupport.utils.Allocator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

public class RakNetEncoder extends MessageToMessageEncoder<RakNetPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RakNetPacket rpacket, List<Object> list) throws Exception {
		ByteBuf buf = Allocator.allocateBuffer();
		buf.writeByte(rpacket.getId());
		rpacket.encode(buf);
		list.add(new DatagramPacket(buf, rpacket.getClientAddress()));
	}

}
