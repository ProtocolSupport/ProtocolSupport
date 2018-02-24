package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
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
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		cache.updatePEPositionLeniency((y - cache.getClientY()) > 0);
		int teleportId = cache.peekTeleportConfirmId();
		if (teleportId != -1) {
			//PE sends AVERAGE positions (FFS Mojang) so sometimes the BoundingBox of the player will collide inadvertently.
			//We fake the servers position in this instance and shrug and resent a rounded position of the player.
			packets.add(MiddleTeleportAccept.create(teleportId));
			cache.payTeleportConfirm();
			double[] serverPos = cache.getTeleportLocation();
			packets.add(MiddlePositionLook.create(serverPos[0], serverPos[1], serverPos[2], yaw, pitch, onGround));
			cache.setLastClientPosition(x, y, z);
			//TODO: Play around more with these numbers to perhaps make things even more smooth.
			x = Math.floor(x * 8) / 8; y = Math.ceil((y + 0.3) * 8) / 8; z = Math.floor(z * 8) / 8;
		} else {
			cache.setLastClientPosition(x, y, z);
		}

		packets.add(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));


		//TODO: (re)move this shit
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
