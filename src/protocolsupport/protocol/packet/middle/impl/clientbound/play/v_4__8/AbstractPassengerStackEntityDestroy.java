package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractPassengerStackEntityDestroy extends MiddleEntityDestroy {

	protected AbstractPassengerStackEntityDestroy(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		for (NetworkEntity entity : entities) {
			writeDestroy(entity, this::writeRemovePassengers, this::writeLeaveVehicle, this::writeDestroyEntity);
		}
	}

	public static void writeDestroy(
		NetworkEntity entity,
		Consumer<NetworkEntityVehicleData> removePassengersFunction,
		Consumer<NetworkEntityVehicleData> leaveVehicleFunction,
		IntConsumer destroyEntityFunction
	) {
		NetworkEntityVehicleData vehicleData = entity.getDataCache().getData(NetworkEntityVehicleData.DATA_KEY);
		if (vehicleData != null) {
			if (vehicleData.hasPassengers()) {
				removePassengersFunction.accept(vehicleData);
			}
			if (vehicleData.hasVehicle()) {
				leaveVehicleFunction.accept(vehicleData);
			}
		}

		destroyEntityFunction.accept(entity.getId());
	}

	protected abstract void writeRemovePassengers(NetworkEntityVehicleData entity);

	protected abstract void writeLeaveVehicle(NetworkEntityVehicleData entity);

	protected abstract void writeDestroyEntity(int entityid);

}
