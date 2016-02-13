package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_5;

import java.io.IOException;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Ping extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		serializer.readUnsignedByte();
	}

	@Override
	public RecyclableCollection<Packet<?>> toNative() throws Exception {
		RecyclableArrayList<Packet<?>> packets = RecyclableArrayList.create();
		PacketCreator hsscreator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START.get());
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString("");
		hsscreator.writeShort(Bukkit.getPort());
		hsscreator.writeVarInt(1);
		packets.add(hsscreator.create());
		packets.add(ServerBoundPacket.STATUS_REQUEST.get());
		return packets;
	}

}
