package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import java.util.List;
import java.util.function.Consumer;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityPassengers;
import protocolsupport.protocol.pipeline.IPacketDataIO;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.JavaSystemProperty;

/*
 * Multi tree of passengers hierarchy is transformed to a stack (depth first, left to right)
 */
public abstract class AbstractPassengerStackEntityPassengers extends MiddleEntityPassengers {

	protected AbstractPassengerStackEntityPassengers(IMiddlePacketInit init) {
		super(init);
	}

	protected static final int stack_max_depth = JavaSystemProperty.getValue("passengerstack.maxdepth", 100, Integer::parseInt);

	public static final class NetworkEntityVehicleData {

		public static final String DATA_KEY = "Enity_VehicleData";

		protected final NetworkEntity entity;

		public NetworkEntityVehicleData(NetworkEntity entity) {
			this.entity = entity;
		}

		private NetworkEntityVehicleData lastPassenger;

		private NetworkEntityVehicleData vehicle;
		private NetworkEntityVehicleData vehiclePassengerPrev;
		private NetworkEntityVehicleData vehiclePassengerNext;

		public NetworkEntity getEntity() {
			return entity;
		}

		public boolean hasPassengers() {
			return lastPassenger != null;
		}

		public boolean hasVehicle() {
			return vehicle != null;
		}

		protected NetworkEntityVehicleData getLastPassenger() {
			return lastPassenger;
		}

		protected NetworkEntityVehicleData getVehicle() {
			return vehicle;
		}

		protected NetworkEntityVehicleData getVehiclePassengerPrev() {
			return vehiclePassengerPrev;
		}

		protected NetworkEntityVehicleData getVehiclePassengerNext() {
			return vehiclePassengerNext;
		}

		protected void addPassenger(NetworkEntityVehicleData passenger) {
			if (passenger.hasVehicle()) {
				throw new IllegalArgumentException("Passenger already has a vehicle");
			}
			if (passenger == this) {
				throw new IllegalArgumentException("Passenger cant be vehicle for itself");
			}
			if (lastPassenger != null) {
				lastPassenger.vehiclePassengerNext = passenger;
				passenger.vehiclePassengerPrev = lastPassenger;
			}
			lastPassenger = passenger;
			passenger.vehicle = this;
		}

		protected void removePassenger(NetworkEntityVehicleData passenger) {
			if (passenger.vehicle != this) {
				throw new IllegalArgumentException("Wrong passenger vehicle");
			}
			NetworkEntityVehicleData prevPassenger = passenger.vehiclePassengerPrev;
			NetworkEntityVehicleData nextPassenger = passenger.vehiclePassengerNext;
			if (prevPassenger != null) {
				prevPassenger.vehiclePassengerNext = nextPassenger;
			}
			if (nextPassenger != null) {
				nextPassenger.vehiclePassengerPrev = prevPassenger;
			}
			if (passenger.equals(lastPassenger)) {
				lastPassenger = prevPassenger;
			}
			passenger.clearVehicle();
		}

		protected void removePassengers(Consumer<NetworkEntityVehicleData> passengerFunc) {
			for (;;) {
				NetworkEntityVehicleData passenger = lastPassenger;
				if (passenger == null) {
					break;
				}
				lastPassenger = passenger.vehiclePassengerPrev;
				if (lastPassenger != null) {
					lastPassenger.vehiclePassengerNext = null;
				}
				passenger.clearVehicle();
				passengerFunc.accept(passenger);
			}
		}

		private void clearVehicle() {
			vehicle = null;
			vehiclePassengerPrev = null;
			vehiclePassengerNext = null;
		}

	}

	@Override
	protected void write() {
		writeVehiclePassengers(
			io, this::createEntityVehicle,
			entity.getDataCache().computeDataIfAbsent(NetworkEntityVehicleData.DATA_KEY, k -> new NetworkEntityVehicleData(entity)),
			entityCache.getEntities(passengersIds)
		);
	}


	public static void writeVehiclePassengers(IPacketDataIO io, SetEntityVehiclePacketSupplier packet, NetworkEntityVehicleData vehicle, List<NetworkEntity> passenegersEntities) {
		vehicle.removePassengers(passeneger -> io.writeClientbound(packet.create(passeneger.getEntity().getId(), -1)));

		NetworkEntityVehicleData stackVehicleBase = vehicle;

		if (!passenegersEntities.isEmpty()) {
			for (NetworkEntity newPassengerEntity : passenegersEntities) {
				NetworkEntityVehicleData newPassenger = newPassengerEntity.getDataCache().computeDataIfAbsent(NetworkEntityVehicleData.DATA_KEY, k -> new NetworkEntityVehicleData(newPassengerEntity));
				if (newPassenger.hasVehicle()) {
					writeLeaveVehicleConnectStack(io, packet, newPassenger);
				}
				NetworkEntityVehicleData stackVehicle = findStackVehicle(stackVehicleBase);
				vehicle.addPassenger(newPassenger);
				io.writeClientbound(packet.create(newPassengerEntity.getId(), stackVehicle.getEntity().getId()));
				stackVehicleBase = newPassenger;
			}
		}

		NetworkEntityVehicleData stackPassenger = findStackPassenger(vehicle);
		if (stackPassenger != null) {
			io.writeClientbound(packet.create(stackPassenger.entity.getId(), findStackVehicle(stackVehicleBase).getEntity().getId()));
		}
	}

	public static void writeLeaveVehicleConnectStack(IPacketDataIO io, SetEntityVehiclePacketSupplier packet, NetworkEntityVehicleData entity) {
		NetworkEntityVehicleData vehicle = entity.getVehicle();
		NetworkEntityVehicleData vehiclePrevPassenger = entity.getVehiclePassengerPrev();
		NetworkEntityVehicleData vehicleNextPassenger = entity.getVehiclePassengerNext();

		vehicle.removePassenger(entity);
		io.writeClientbound(packet.create(entity.getEntity().getId(), -1));

		NetworkEntityVehicleData stackVehicle = findStackVehicle(vehiclePrevPassenger != null ? vehiclePrevPassenger : vehicle);
		if (vehicleNextPassenger != null) {
			io.writeClientbound(packet.create(vehicleNextPassenger.getEntity().getId(), stackVehicle.getEntity().getId()));
		} else {
			NetworkEntityVehicleData stackPassenger = findStackPassenger(vehicle);
			if (stackPassenger != null) {
				io.writeClientbound(packet.create(stackPassenger.getEntity().getId(), stackVehicle.getEntity().getId()));
			}
		}
	}

	protected static NetworkEntityVehicleData findStackPassenger(NetworkEntityVehicleData modifiedVehicle) {
		for (int i = 0; i < stack_max_depth; i++) {
			NetworkEntityVehicleData modifiedEntityVehicleNextPassenger = modifiedVehicle.getVehiclePassengerNext();
			if (modifiedEntityVehicleNextPassenger != null) {
				return modifiedEntityVehicleNextPassenger;
			}
			modifiedVehicle = modifiedVehicle.getVehicle();
			if (modifiedVehicle == null) {
				return null;
			}
		}
		throw new IllegalStateException("Stack passenger max search depth hit");
	}

	protected static NetworkEntityVehicleData findStackVehicle(NetworkEntityVehicleData vehicle) {
		for (int i = 0; i < stack_max_depth; i++) {
			NetworkEntityVehicleData lastPassenger = vehicle.getLastPassenger();
			if (lastPassenger == null) {
				return vehicle;
			}
			vehicle = lastPassenger;
		}
		throw new IllegalStateException("Stack vehicle max search depth hit");
	}

	@FunctionalInterface
	public static interface SetEntityVehiclePacketSupplier {
		public ClientBoundPacketData create(int passengerId, int vehicleId);
	}

	protected abstract ClientBoundPacketData createEntityVehicle(int passengerId, int vehicleId);

}
