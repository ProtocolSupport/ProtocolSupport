package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;

public abstract class MiddleEntityPassengers extends MiddleEntityData {

	protected MiddleEntityPassengers(IMiddlePacketInit init) {
		super(init);
	}

	protected int[] passengersIds;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		passengersIds = ArrayCodec.readVarIntVarIntArray(serverdata);
	}

}
