package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarLong(clientdata); //entity id
		x = clientdata.readFloatLE();
		y = clientdata.readFloatLE() - 1.6200000047683716D;
		z = clientdata.readFloatLE();
		pitch = clientdata.readFloatLE();
		yaw = clientdata.readFloatLE();
		clientdata.readFloatLE(); //head yaw
		clientdata.readByte(); //mode
		onGround = clientdata.readBoolean();
		VarNumberSerializer.readVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		MovementCache movecache = cache.getMovementCache();
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		movecache.updatePEPositionLeniency(y);
		movecache.setPEClientPosition(x, y, z);
		//PE doesn't send a movement confirm after position set, so we just confirm teleport straight away
		int teleportId = movecache.teleportConfirm();
		if (teleportId != -1) {
			packets.add(MiddleTeleportAccept.create(teleportId));
			packets.add(MiddlePositionLook.create(movecache.getX(), movecache.getY(), movecache.getZ(), yaw, pitch, onGround));
		}
		packets.add(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));

//		//TODO: fix or move this shit
//		if (cache.getSignTag() != null) {
//			NBTTagCompoundWrapper signTag = cache.getSignTag();
//			int x = signTag.getIntNumber("x");
//			int y = signTag.getIntNumber("y");
//			int z = signTag.getIntNumber("z");
//
//			String[] nbtLines = new String[4];
//			String[] lines = signTag.getString("Text").split("\n");
//			for (int i = 0; i < nbtLines.length; i++) {
//				if (lines.length > i) {
//					nbtLines[i] = lines[i];
//				} else {
//					nbtLines[i] = "";
//				}
//			}
//			packets.add(MiddleUpdateSign.create(new Position(x, y, z), nbtLines));
//			cache.setSignTag(null);
//		}

		return packets;
	}

}
