package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PositionLook extends MiddlePositionLook {

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData flying = ServerBoundPacketData.create(ServerBoundPacket.PLAY_PLAYER);
		flying.writeBoolean(onGround);
		packets.add(flying);
		RecyclableCollection<ServerBoundPacketData> superPackets = super.toNative();
		try {
			packets.addAll(superPackets);
		} finally {
			superPackets.recycleObjectOnly();
		}
		if (cache.getSignTag() != null) { // If the player was writing a sign...
			NBTTagCompoundWrapper signTag = cache.getSignTag();
			int x = signTag.getIntNumber("x");
			int y = signTag.getIntNumber("y");
			int z = signTag.getIntNumber("z");

			String[] nbtLines = new String[4];
			String[] lines = signTag.getString("Text").split("\n"); // Lines in MCPE are separated by new lines
			for (int i = 0; nbtLines.length > i; i++) {
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

}
