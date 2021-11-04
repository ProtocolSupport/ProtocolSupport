package protocolsupport.protocol.packet.middle.base.serverbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.IMiddlePacket;

public interface IServerboundMiddlePacket extends IMiddlePacket {

	public void decode(ByteBuf clientdata);

}
