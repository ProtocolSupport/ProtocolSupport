package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class RiderJump extends ServerBoundMiddlePacket {

	protected int riderId;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		riderId = (int) VarNumberSerializer.readVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		//TODO: Why this not work? :F
		packets.add(MiddleEntityAction.create(riderId, MiddleEntityAction.Action.START_JUMP, 100));
		packets.add(MiddleSteerVehicle.create(0, 0, 0x1)); // 0x1 = jump vehicle
		return packets;
	}

}
