package protocolsupport.protocol.transformer.middlepacket;

import java.io.IOException;
import java.util.Collection;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.PacketDataSerializer;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public abstract void readFromClientData(PacketDataSerializer serializer) throws IOException;

	public abstract Collection<Packet<?>> toNative() throws Exception;

}
