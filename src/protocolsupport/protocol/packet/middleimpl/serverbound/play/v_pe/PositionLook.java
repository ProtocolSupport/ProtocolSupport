package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

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
		x = MiscSerializer.readLFloat(clientdata);
		y = MiscSerializer.readLFloat(clientdata) - 1.6200000047683716D;
		z = MiscSerializer.readLFloat(clientdata);
		pitch = MiscSerializer.readLFloat(clientdata);
		yaw = MiscSerializer.readLFloat(clientdata);
		MiscSerializer.readLFloat(clientdata); //head yaw
		clientdata.readByte(); //mode
		onGround = clientdata.readBoolean();
		VarNumberSerializer.readVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		int teleportId = cache.tryTeleportConfirm(x, y, z);
		if (teleportId != -1) {
			packets.add(MiddleTeleportAccept.create(teleportId));
		}
		//if(cache.getWatchedSelf().getDataCache().riderInfo != null) {
			//NetworkEntity vehicle = cache.getWatchedSelf().getDataCache().riderInfo.getVehicle();
			//TODO: Add different packets in here. For instance if you're on a pig, you can only look around (I think) and horses you need to control etc.
		//}
		packets.add(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));

		//TODO: remove this shit
		if (cache.getSignTag() != null) {
			NBTTagCompoundWrapper signTag = cache.getSignTag();
			int x = signTag.getIntNumber("x");
			int y = signTag.getIntNumber("y");
			int z = signTag.getIntNumber("z");

			String[] nbtLines = new String[4];
			String[] lines = signTag.getString("Text").split("\n");
			for (int i = 0; i < nbtLines.length; i++) {
				if (lines.length > i) {
					nbtLines[i] = lines[i];
				} else {
					nbtLines[i] = "";
				}
			}
			packets.add(MiddleUpdateSign.create(new Position(x, y, z), nbtLines));
			cache.setSignTag(null);
		}

		return packets;
	}

}
