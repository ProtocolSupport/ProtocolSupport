package protocolsupport.protocol.packet.middle;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	protected SharedStorage sharedstorage;

	public void setSharedStorage(SharedStorage sharedstorage) {
		this.sharedstorage = sharedstorage;
	}

	public abstract void readFromClientData(PacketDataSerializer serializer) throws IOException;

	public abstract RecyclableCollection<? extends Packet<?>> toNative() throws Exception;

}
