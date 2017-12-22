package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class MoveVehicle extends ServerBoundMiddlePacket {

	protected long vehicle;
	protected float x, y, z;
	protected byte pitch, headYaw, yaw;
	protected boolean teleported, onGround;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		vehicle = VarNumberSerializer.readVarLong(clientdata);
		x = MiscSerializer.readLFloat(clientdata);
		y = MiscSerializer.readLFloat(clientdata);
		z = MiscSerializer.readLFloat(clientdata);
		pitch = clientdata.readByte();
		headYaw = clientdata.readByte();
		yaw = clientdata.readByte();
		onGround = clientdata.readBoolean();
		teleported = clientdata.readBoolean();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		System.out.println("VEH-MOVE: " + vehicle + " x-" + x + " y-" + y + " z-" + z + " pitch-" + pitch + " headYaw-" + headYaw + " yaw-" + yaw + "onGround" + onGround);
		return RecyclableEmptyList.get();
	}

}
