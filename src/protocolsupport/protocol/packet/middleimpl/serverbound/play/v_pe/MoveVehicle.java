package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveVehicle;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketOffset;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class MoveVehicle extends ServerBoundMiddlePacket {

	protected int vehicleId;
	protected float x, y, z;
	protected byte pitch, headYaw, yaw;
	protected boolean teleported, onGround;
	protected static byte lastYaw = 0;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		vehicleId = (int) VarNumberSerializer.readVarLong(clientdata);
		x = clientdata.readFloatLE();
		y = clientdata.readFloatLE();
		z = clientdata.readFloatLE();
		pitch = clientdata.readByte();
		headYaw = clientdata.readByte();
		yaw = clientdata.readByte();
		onGround = clientdata.readBoolean();
		teleported = clientdata.readBoolean();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity vehicle = cache.getWatchedEntity(vehicleId);
		if (vehicle != null) {
			if(vehicle.getType() == NetworkEntityType.BOAT) {
				packets.add(MiddleSteerBoat.create(cache.isRightPaddleTurning(), cache.isLeftPaddleTurning()));
			}
			PocketEntityData typeData = PocketData.getPocketEntityData(vehicle.getType());
			if (typeData != null && typeData.getOffset() != null) {
				PocketOffset offset = typeData.getOffset();
				x -= offset.getX();
				y -= offset.getY();
				z -= offset.getZ();
				pitch = (byte) Utils.shortDegree(pitch - offset.getPitch(), 256);
				yaw = (byte) Utils.shortDegree(yaw - offset.getYaw(), 256);
			}
		}
		lastYaw = yaw;
		float realPitch = (360f/256f) * pitch;
		float realYaw = (360f/256f) * yaw;
		packets.add(MiddleMoveVehicle.create(x, y, z, realYaw, realPitch));
		return packets;
	}
	
	public static byte getLastYaw() {
		return lastYaw;
	}

}
