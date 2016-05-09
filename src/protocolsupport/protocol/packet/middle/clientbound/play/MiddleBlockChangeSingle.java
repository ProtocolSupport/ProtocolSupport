package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;

public abstract class MiddleBlockChangeSingle<T> extends MiddleBlock<T> {

	protected int id;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		id = serializer.readVarInt();
	}

}
