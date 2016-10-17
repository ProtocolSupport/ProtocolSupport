package protocolsupport.protocol.packet.middle;

import java.io.IOException;

import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	protected NetworkDataCache sharedstorage;

	@Override
	public void setSharedStorage(NetworkDataCache sharedstorage) {
		this.sharedstorage = sharedstorage;
	}

	public abstract void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException;

	public abstract RecyclableCollection<PacketCreator> toNative() throws Exception;

}
