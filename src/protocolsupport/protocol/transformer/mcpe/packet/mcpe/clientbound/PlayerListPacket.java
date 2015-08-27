package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class PlayerListPacket implements ClientboundPEPacket {

	protected int action; //0 - add, 1 - remove
	protected ArrayList<PlayerListEntry> entries = new ArrayList<PlayerListEntry>();

	public PlayerListPacket(EntityPlayer... players) {
		this.action = 0;
		for (EntityPlayer player : players) {
			entries.add(new PlayerListEntry(player.getUniqueID(), player.getName(), player.getId()));
		}
	}

	public PlayerListPacket(UUID... uuids) {
		this.action = 1;
		for (UUID uuid : uuids) {
			entries.add(new PlayerListEntry(uuid));
		}
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
					serializer.writeLong(entry.entityId);
					serializer.writeString(entry.name);
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

	protected static class PlayerListEntry {
		private UUID uuid;
		private String name;
		private int entityId;
		public PlayerListEntry(UUID uuid, String name, int entityId) {
			this.uuid = uuid;
			this.name = name;
			this.entityId = entityId;
		}
		public PlayerListEntry(UUID uuid) {
			this.uuid = uuid;
		}
	}

}
