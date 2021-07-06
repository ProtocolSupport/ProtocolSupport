package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpectate;

public class Spectate extends MiddleSpectate {

	public Spectate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		entityUUID = UUIDCodec.readUUID2L(clientdata);
	}

}
