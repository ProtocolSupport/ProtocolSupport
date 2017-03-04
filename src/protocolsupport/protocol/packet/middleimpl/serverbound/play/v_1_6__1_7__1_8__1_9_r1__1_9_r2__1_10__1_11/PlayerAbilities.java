package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		flags = clientdata.readUnsignedByte();
		flySpeed = clientdata.readFloat();
		walkSpeed = clientdata.readFloat();
	}

}
