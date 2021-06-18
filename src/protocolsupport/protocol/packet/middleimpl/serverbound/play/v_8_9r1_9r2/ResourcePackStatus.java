package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	public ResourcePackStatus(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		StringCodec.readVarIntUTF8String(clientdata, 40);
		result = VarNumberCodec.readVarInt(clientdata);
	}

}
