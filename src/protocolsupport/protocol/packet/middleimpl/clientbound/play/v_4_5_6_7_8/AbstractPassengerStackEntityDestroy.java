package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractPassengerStackEntityDestroy extends MiddleEntityDestroy {

	public AbstractPassengerStackEntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	protected ArrayList<NetworkEntity> entities;

	@Override
	protected void handleReadData() {
		entities = entityCache.popEntities(entityIds);
	}

	@Override
	protected void writeToClient() {
		writeDestroy(entities, this::writeRemovePassengers, this::writeLeaveVehicle, this::writeDestroyEntities);
	}

	public static <A extends List<NetworkEntity> & RandomAccess> void writeDestroy(
		A entities,
		Consumer<NetworkEntityVehicleData> removePassengersFunction,
		Consumer<NetworkEntityVehicleData> leaveVehicleFunction,
		Consumer<A> destroyEntitiesFunction
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

		destroyEntitiesFunction.accept(entities);
	}

	protected abstract void writeRemovePassengers(NetworkEntityVehicleData entity);

	protected abstract void writeLeaveVehicle(NetworkEntityVehicleData entity);

	protected abstract <A extends List<NetworkEntity> & RandomAccess> void writeDestroyEntities(A entities);

}
