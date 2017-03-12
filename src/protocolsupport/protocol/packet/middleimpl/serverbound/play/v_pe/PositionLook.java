package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

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
		return packets;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		VarNumberSerializer.readVarLong(clientdata); //entity id
		x = MiscSerializer.readLFloat(clientdata);
		y = MiscSerializer.readLFloat(clientdata) - 1.6200000047683716D;
		z = MiscSerializer.readLFloat(clientdata);
		pitch = MiscSerializer.readLFloat(clientdata);
		yaw = MiscSerializer.readLFloat(clientdata);
		MiscSerializer.readLFloat(clientdata); //head yaw
		clientdata.readByte(); //mode
		onGround = clientdata.readBoolean();
	}

}
