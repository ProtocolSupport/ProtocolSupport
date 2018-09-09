package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class RiderJump extends ServerBoundMiddlePacket {

	public RiderJump(ConnectionImpl connection) {
		super(connection);
	}

	protected long jumptime;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		jumptime = VarNumberSerializer.readVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		int selfId = cache.getWatchedEntityCache().getSelfPlayerEntityId();
		packets.add(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.START_JUMP, (int) jumptime / 2));
		packets.add(MiddleSteerVehicle.create(0, 0, 0x1)); // 0x1 = jump vehicle
		packets.add(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.STOP_JUMP, 0));
		return packets;
	}

}
