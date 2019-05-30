package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PositionLook extends ServerBoundMiddlePacket {

	public PositionLook(ConnectionImpl connection) {
		super(connection);
	}

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
		double yOffset = 0;
		if (onGround) {
			final double dX = x - movecache.getPEClientX();
			final double dZ = z - movecache.getPEClientZ();
			final double speed = Math.sqrt(dX * dX + dZ * dZ);
			//TODO: figure out actual values here, should just be a normal line: offset = C1 * speed + C2
			final double speedOffset = speed >= 0.25 ? 0.9 : 0.6;
			yOffset = (y - movecache.getPEClientY()) > 0.01 ? speedOffset : 0;
			movecache.updatePEPositionLeniency(y);
		}
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
				yaw = ((360f / 256f) * cache.getAttributesCache().getPELastVehicleYaw()) + yaw + 90;
			}
		}
		packets.add(MiddleMoveLook.create(x, y + yOffset, z, yaw, pitch, onGround));
		if (cache.getPETileCache().shouldSignSign()) {
			cache.getPETileCache().signSign(packets);
		}
		return packets;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}
}
