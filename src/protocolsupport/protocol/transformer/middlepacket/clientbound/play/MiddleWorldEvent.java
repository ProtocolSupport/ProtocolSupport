package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

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
