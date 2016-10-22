package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_5;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Ping extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readUnsignedByte();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative()  {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString("");
		hsscreator.writeShort(Bukkit.getPort());
		hsscreator.writeVarInt(1);
		packets.add(hsscreator);
		packets.add(ServerBoundPacketData.create(ServerBoundPacket.STATUS_REQUEST));
		return packets;
	}

}
