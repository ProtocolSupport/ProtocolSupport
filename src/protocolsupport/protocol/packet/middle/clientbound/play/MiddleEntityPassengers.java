package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;

public abstract class MiddleEntityPassengers extends MiddleEntityData {

	protected MiddleEntityPassengers(MiddlePacketInit init) {
		super(init);
	}

	protected int[] passengersIds;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		passengersIds = ArrayCodec.readVarIntVarIntArray(serverdata);
	}

}
