package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;

public class Ping extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		serializer.readUnsignedByte();
	}

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
		PacketCreator hsscreator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START.get());
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString("");
		hsscreator.writeShort(Bukkit.getPort());
		hsscreator.writeVarInt(1);
		packets.add(hsscreator.create());
		packets.add(ServerBoundPacket.STATUS_PING.get());
		return packets;
	}

}
