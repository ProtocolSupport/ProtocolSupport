package protocolsupport.protocol.transformer.middlepacket;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public abstract void readFromClientData(PacketDataSerializer serializer) throws IOException;

	public abstract RecyclableCollection<? extends Packet<?>> toNative() throws Exception;

}
