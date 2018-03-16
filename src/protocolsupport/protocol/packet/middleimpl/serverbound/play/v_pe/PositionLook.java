package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.storage.netcache.MovementCache;
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
	protected long vehicle;
	protected int huh1;
	protected int huh2;
	protected double fakeYaw;

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
		vehicle = VarNumberSerializer.readVarLong(clientdata);
		if (mode == 2) {
			huh1 = VarNumberSerializer.readSVarInt(clientdata);
			huh2 = VarNumberSerializer.readSVarInt(clientdata);
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		MovementCache movecache = cache.getMovementCache();
		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
		movecache.updatePEPositionLeniency(y);
		movecache.setPEClientPosition(x, y, z);
		//PE doesn't send a movement confirm after position set, so we just confirm teleport straight away
		int teleportId = movecache.teleportConfirm();
		if (teleportId != -1) {
			packets.add(MiddleTeleportAccept.create(teleportId));
			packets.add(MiddlePositionLook.create(movecache.getX(), movecache.getY(), movecache.getZ(), headYaw, pitch, onGround));
			//TODO: Play around more with these numbers to perhaps make things even more smooth. (Round to 8ths of blocks?)
			//x = Math.floor(x * 8) / 8; y = Math.ceil((y + 0.3) * 8) / 8; z = Math.floor(z * 8) / 8;
		}
		if (player.getDataCache().isRiding()) {
			NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(player.getDataCache().getVehicleId());
			if (vehicle.isOfType(NetworkEntityType.BOAT)) {
				yaw = (360f/256f) * MoveVehicle.getLastVehicleYaw() + yaw + 90;
			}
		}
		packets.add(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));
		BlockTileUpdate.trySignSign(packets);
		return packets;
	}

}
