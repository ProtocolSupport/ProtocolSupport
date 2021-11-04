package protocolsupport.protocol.packet.middle.base.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.IMiddlePacket;

public interface IClientboundMiddlePacket extends IMiddlePacket {

	void encode(ByteBuf serverdata);

}
