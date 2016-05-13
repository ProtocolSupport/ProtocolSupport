package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.UDPNetworkManager;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class PlayerListPacket implements ClientboundPEPacket {

	protected int action; //0 - add, 1 - remove
	protected ArrayList<PEPlayerListEntry> entries = new ArrayList<PEPlayerListEntry>();

	public PlayerListPacket(boolean add, Collection<PEPlayerListEntry> players) {
		this.action = add ? 0 : 1;
		entries.addAll(players);
	}

	@Override
	public int getId() {
		return PEPacketIDs.PLAYER_LIST_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeByte(action);
		serializer.writeInt(entries.size());
		switch (action) {
			case 0: {
				for (PEPlayerListEntry entry : entries) {
					serializer.writeUUID(entry.uuid);
					serializer.writeLong(-1);
					serializer.writeString(entry.name);
					serializer.writeString("Default");
					serializer.writeArray(UDPNetworkManager.defaultSkin);
				}
				break;
			}
			case 1: {
				for (PEPlayerListEntry entry : entries) {
					serializer.writeUUID(entry.uuid);
				}
				break;
			}
			default: {
				break;
			}
		}
		return this;
	}

	public static class PEPlayerListEntry {
		protected UUID uuid;
		protected String name;
		public PEPlayerListEntry(UUID uuid, String name) {
			this.uuid = uuid;
			this.name = name;
		}
		public PEPlayerListEntry(UUID uuid) {
			this.uuid = uuid;
		}
	}

}
