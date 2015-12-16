package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

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
	public Collection<Packet<?>> toNative() throws Exception {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_LOOK.get());
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return Collections.<Packet<?>>singletonList(creator.create());
		} else {
			PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_POSITION_LOOK.get());
			creator.writeDouble(x);
			creator.writeDouble(y);
			creator.writeDouble(z);
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return Collections.<Packet<?>>singletonList(creator.create());
		}
	}

}
