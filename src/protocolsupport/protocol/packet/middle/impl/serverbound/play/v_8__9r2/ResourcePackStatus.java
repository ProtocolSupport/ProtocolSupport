package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8__9r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;

public class ResourcePackStatus extends MiddleResourcePackStatus implements
IServerboundMiddlePacketV8,
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2 {

	public ResourcePackStatus(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		StringCodec.readVarIntUTF8String(clientdata, 40);
		result = VarNumberCodec.readVarInt(clientdata);
	}

}
