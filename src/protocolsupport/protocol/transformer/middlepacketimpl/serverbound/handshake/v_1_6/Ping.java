package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class Ping extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		serializer.readUnsignedByte();
		serializer.readUnsignedByte();
		serializer.readString(Short.MAX_VALUE);
		serializer.readUnsignedShort();
		serializer.readUnsignedByte();
		hostname = serializer.readString(Short.MAX_VALUE);
		port = serializer.readInt();
	}

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
		PacketCreator hsscreator = new PacketCreator(ServerBoundPacket.HANDSHAKE_START.get());
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString(hostname);
		hsscreator.writeShort(port);
		hsscreator.writeVarInt(1);
		packets.add(hsscreator.create());
		packets.add(ServerBoundPacket.STATUS_PING.get());
		return packets;
	}

}
