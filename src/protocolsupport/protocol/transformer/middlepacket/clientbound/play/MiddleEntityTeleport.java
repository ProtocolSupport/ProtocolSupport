package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;

public abstract class MiddleEntityTeleport<T> extends MiddleEntity<T> {

	protected int x;
	protected int y;
	protected int z;
	protected byte yaw;
	protected byte pitch;
	protected WatchedEntity wentity;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		yaw = serializer.readByte();
		pitch = serializer.readByte();
	}

	@Override
	public void handle(LocalStorage storage) {
		wentity = storage.getWatchedEntity(entityId);
	}

}
