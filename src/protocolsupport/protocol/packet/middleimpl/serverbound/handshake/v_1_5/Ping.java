package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_5;

import java.io.IOException;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Ping extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		serializer.readUnsignedByte();
	}

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		RecyclableArrayList<PacketCreator> packets = RecyclableArrayList.create();
		PacketCreator hsscreator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START);
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString("");
		hsscreator.writeShort(Bukkit.getPort());
		hsscreator.writeVarInt(1);
		packets.add(hsscreator);
		packets.add(PacketCreator.create(ServerBoundPacket.STATUS_REQUEST));
		return packets;
	}

}
