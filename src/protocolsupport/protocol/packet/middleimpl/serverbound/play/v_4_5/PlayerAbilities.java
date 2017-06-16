package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		flags = clientdata.readUnsignedByte();
		flySpeed = clientdata.readUnsignedByte() / 255.0F;
		walkSpeed = clientdata.readUnsignedByte() / 255.0F;
	}

}
