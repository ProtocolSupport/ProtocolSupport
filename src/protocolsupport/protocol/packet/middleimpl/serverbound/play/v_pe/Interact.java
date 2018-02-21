package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Interact extends ServerBoundMiddlePacket {

	protected short peAction;
	protected int targetId;
	protected Vector interactAt;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		peAction = clientdata.readUnsignedByte();
		targetId = (int) VarNumberSerializer.readVarLong(clientdata);
		if (peAction == MOUSE_OVER) {
			interactAt = new Vector(clientdata.readFloatLE(), clientdata.readFloatLE(), clientdata.readFloatLE());
		}
	}

	private static final int LEAVE_VEHICLE = 3;
	private static final int MOUSE_OVER = 4;
	private static final int OPEN_INVENTORY = 6;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch (peAction) {
			case LEAVE_VEHICLE: {
				packets.add(MiddleSteerVehicle.create(0, 0, 0x2)); // 0x2 = unmount vehicle
				break;
			}
			case MOUSE_OVER: {
				break;
			}
			case OPEN_INVENTORY: {
				break;
			}
		}
		return packets;
	}
}
