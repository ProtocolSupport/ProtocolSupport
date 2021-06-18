package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleEntityPassengers extends ClientBoundMiddlePacket {

	protected MiddleEntityPassengers(MiddlePacketInit init) {
		super(init);
	}

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	protected void decode(ByteBuf serverdata) {
		vehicleId = VarNumberCodec.readVarInt(serverdata);
		passengersIds = ArrayCodec.readVarIntVarIntArray(serverdata);
	}

}
