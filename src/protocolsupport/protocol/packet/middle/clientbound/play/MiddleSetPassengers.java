package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleSetPassengers extends ClientBoundMiddlePacket {

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		vehicleId = VarNumberSerializer.readVarInt(serverdata);
		passengersIds = ArraySerializer.readVarIntVarIntArray(serverdata);
	}
	
	@Override
	public void handle() {
		NetworkEntity vehicle = cache.getWatchedEntity(vehicleId);
		if(vehicle != null) {
			for(int passengerId : passengersIds) {
				NetworkEntity passenger = cache.getWatchedEntity(passengerId);
				if(passenger != null) {
					passenger.updatePosition(vehicle.getPosition());
					passenger.updateRotation(vehicle.getYaw(), vehicle.getPitch());
				}
			}
		}
	}

}
