package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.BlockPosition;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleWorldEvent<T> extends ClientBoundMiddlePacket<T> {

	protected int effectId;
	protected BlockPosition position;
	protected int data;
	protected boolean disableRelative;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		effectId = serializer.readInt();
		position = serializer.readPosition();
		data = serializer.readInt();
		disableRelative = serializer.readBoolean();
	}

}
