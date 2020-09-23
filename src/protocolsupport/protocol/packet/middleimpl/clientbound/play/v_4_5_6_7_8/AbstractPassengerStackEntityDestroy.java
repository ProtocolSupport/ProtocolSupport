package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import java.util.List;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractPassengerStackEntityDestroy extends MiddleEntityDestroy {

	public AbstractPassengerStackEntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	protected List<NetworkEntity> entities;

	@Override
	protected void handleReadData() {
		entities = entityCache.popEntities(entityIds);
	}

	@Override
	protected void writeToClient() {
		writeDestroy(entityIds, entities, this::writeRemovePassengers, this::writeLeaveVehicle, this::writeDestroyEntities);
	}

	public static void writeDestroy(
		int[] entitiesIds, List<NetworkEntity> entities,
		Consumer<NetworkEntityVehicleData> removePassengersFunction,
		Consumer<NetworkEntityVehicleData> leaveVehicleFunction,
		Consumer<int[]> destroyEntitiesFunction
	) {
		for (NetworkEntity entity : entities) {
			NetworkEntityVehicleData vehicleData = entity.getDataCache().getData(NetworkEntityVehicleData.DATA_KEY);
			if (vehicleData != null) {
				if (vehicleData.hasPassengers()) {
					removePassengersFunction.accept(vehicleData);
				}
				if (vehicleData.hasVehicle()) {
					leaveVehicleFunction.accept(vehicleData);
				}
			}
		}

		destroyEntitiesFunction.accept(entitiesIds);
	}

	protected abstract void writeRemovePassengers(NetworkEntityVehicleData entity);

	protected abstract void writeLeaveVehicle(NetworkEntityVehicleData entity);

	protected abstract void writeDestroyEntities(int[] entitiesIds);

}
