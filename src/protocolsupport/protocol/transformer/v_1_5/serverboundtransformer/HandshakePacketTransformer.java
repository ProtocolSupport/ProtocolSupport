package protocolsupport.protocol.transformer.v_1_5.serverboundtransformer;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.utils.PacketCreator;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws Exception {
		switch (packetId) {
			case 0xFE: {
				ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
				PacketCreator hsscreator = new PacketCreator(ServerboundPacket.HANDSHAKE_START.get());
				serializer.readUnsignedByte();
				hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
				hsscreator.writeString("");
				hsscreator.writeShort(Bukkit.getPort());
				hsscreator.writeVarInt(1);
				packets.add(hsscreator.create());
				packets.add(ServerboundPacket.STATUS_PING.get());
				return packets;
			}
			case 0x02: {
				ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
				PacketCreator hsscreator = new PacketCreator(ServerboundPacket.HANDSHAKE_START.get());
				serializer.readUnsignedByte();
				hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
				String username = serializer.readString(16);
				hsscreator.writeString(serializer.readString(32767));
				hsscreator.writeShort(serializer.readInt());
				hsscreator.writeVarInt(2);
				packets.add(hsscreator.create());
				PacketCreator lscreator = new PacketCreator(ServerboundPacket.LOGIN_START.get());
				lscreator.writeString(username);
				packets.add(lscreator.create());
				return packets;
			}
		}
		return null;
	}

}
