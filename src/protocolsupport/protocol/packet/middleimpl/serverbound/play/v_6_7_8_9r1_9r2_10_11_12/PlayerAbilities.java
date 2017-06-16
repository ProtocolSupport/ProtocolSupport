package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12;

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
