package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class PlayerListPacket implements ClientboundPEPacket {

	protected int action; //0 - add, 1 - remove
	protected ArrayList<PlayerListEntry> entries = new ArrayList<PlayerListEntry>();

	public PlayerListPacket(boolean add, Collection<PlayerListEntry> players) {
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
				for (PlayerListEntry entry : entries) {
					serializer.writeUUID(entry.uuid);
					serializer.writeLong(-1);
					serializer.writeString(entry.name);
					serializer.writeBoolean(true);
					serializer.writeBoolean(false);
					serializer.writeArray(UDPNetworkManager.defaultSkin);
				}
				break;
			}
			case 1: {
				for (PlayerListEntry entry : entries) {
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

	public static class PlayerListEntry {
		protected UUID uuid;
		protected String name;
		public PlayerListEntry(UUID uuid, String name) {
			this.uuid = uuid;
			this.name = name;
		}
		public PlayerListEntry(UUID uuid) {
			this.uuid = uuid;
		}
	}

}
