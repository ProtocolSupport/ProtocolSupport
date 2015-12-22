package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double yhead;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 yhead = serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

	@Override
	public RecyclableCollection<Packet<?>> toNative() throws Exception {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_LOOK.get());
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.<Packet<?>>create(creator.create());
		} else {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_POSITION_LOOK.get());
			creator.writeDouble(x);
			creator.writeDouble(y);
			creator.writeDouble(z);
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.<Packet<?>>create(creator.create());
		}
	}

}
