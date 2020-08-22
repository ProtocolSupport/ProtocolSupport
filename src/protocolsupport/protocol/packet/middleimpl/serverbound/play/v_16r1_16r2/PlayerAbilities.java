package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		flags = clientdata.readByte();
	}

}
