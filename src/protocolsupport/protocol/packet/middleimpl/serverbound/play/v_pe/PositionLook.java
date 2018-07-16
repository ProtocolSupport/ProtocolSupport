package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float pitch;
	protected float yaw;
	protected float headYaw;
	protected byte mode;
	protected boolean onGround;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarLong(clientdata); //entity id
		x = clientdata.readFloatLE();
		y = clientdata.readFloatLE() - 1.6200000047683716D;
		z = clientdata.readFloatLE();
		pitch = clientdata.readFloatLE();
		headYaw = clientdata.readFloatLE();
		yaw = clientdata.readFloatLE();
		mode = clientdata.readByte(); //mode
		onGround = clientdata.readBoolean();
		VarNumberSerializer.readVarLong(clientdata);
		if (mode == 2) {
			clientdata.readIntLE();
			clientdata.readIntLE();
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		MovementCache movecache = cache.getMovementCache();
		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
		//movecache.updatePEPositionLeniency(y);
		movecache.setPEClientPosition(x, y, z);
		//PE doesn't send a movement confirm after position set, so we just confirm teleport straight away
		int teleportId = movecache.teleportConfirm();
		if (teleportId != -1) {
			packets.add(MiddleTeleportAccept.create(teleportId));
			packets.add(MiddleMoveLook.create(movecache.getX(), movecache.getY(), movecache.getZ(), headYaw, pitch, onGround));
		}
		//yaw fix for boats due to relative vs absolute
		if (player.getDataCache().isRiding()) {
			NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(player.getDataCache().getVehicleId());
			if (vehicle.getType() == NetworkEntityType.BOAT) {
				yaw = ((360f/256f) * cache.getAttributesCache().getPELastVehicleYaw()) + yaw + 90;
			}
		}
		packets.add(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		if (cache.getPETileCache().shouldSignSign()) {
			cache.getPETileCache().signSign(packets);
		}
		return packets;
	}

}
