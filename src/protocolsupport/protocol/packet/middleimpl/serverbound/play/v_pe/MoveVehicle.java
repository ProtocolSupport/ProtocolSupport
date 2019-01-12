package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveVehicle;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.Offset;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class MoveVehicle extends ServerBoundMiddlePacket {

	public MoveVehicle(ConnectionImpl connection) {
		super(connection);
	}

	protected int vehicleId;
	protected float x, y, z;
	protected byte pitch, headYaw, yaw;
	protected boolean teleported, onGround;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		vehicleId = (int) VarNumberSerializer.readVarLong(clientdata);
		byte flag = clientdata.readByte();
		onGround = (flag & 128) == 128;
		teleported = (flag & 64) == 64;
		x = clientdata.readFloatLE();
		y = clientdata.readFloatLE();
		z = clientdata.readFloatLE();
		pitch = clientdata.readByte();
		yaw = clientdata.readByte();
		headYaw = clientdata.readByte();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(vehicleId);
		if (vehicle != null) {
			if (vehicle.getType() == NetworkEntityType.BOAT) {
				packets.add(MiddleSteerBoat.create(
					cache.getMovementCache().isPERightPaddleTurning(),
					cache.getMovementCache().isPELeftPaddleTurning())
				);
			}
			PEEntityData typeData = PEDataValues.getEntityData(vehicle.getType());
			if ((typeData != null) && (typeData.getOffset() != null)) {
				Offset offset = typeData.getOffset();
				x -= offset.getX();
				y -= offset.getY();
				z -= offset.getZ();
				pitch -= offset.getPitch();
				yaw -= offset.getYaw();
			}
		}
		cache.getAttributesCache().setPELastVehicleYaw(yaw);
		float realPitch = (360f / 256f) * pitch;
		float realYaw = (360f / 256f) * yaw;
		packets.add(MiddleMoveVehicle.create(x, y, z, realYaw, realPitch));
		return packets;
	}

}
