package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import gnu.trove.map.TIntObjectMap;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.PEDataWatcherSerializer;
import protocolsupport.utils.DataWatcherObject;

public class AddPlayerPacket implements ClientboundPEPacket {

	protected UUID uuid;
	protected String name;
	protected int entityId;
	protected float locX;
	protected float locY;
	protected float locZ;
	protected float yaw;
	protected float pitch;
	protected ItemStack item;
	protected TIntObjectMap<DataWatcherObject> meta;

	public AddPlayerPacket(UUID uuid, String name, int entityId, float locX, float locY, float locZ, float yaw, float pitch, TIntObjectMap<DataWatcherObject> meta) {
		this.uuid = uuid;
		this.name = name;
		this.entityId = entityId;
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
		this.yaw = yaw;
		this.pitch = pitch;
		this.meta = meta;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADD_PLAYER_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeUUID(uuid);
		serializer.writeString(name);
		serializer.writeLong(entityId);
		serializer.writeFloat(locX);
		serializer.writeFloat(locY);
		serializer.writeFloat(locZ);
		serializer.writeFloat(0);
		serializer.writeFloat(0);
		serializer.writeFloat(0);
		serializer.writeFloat(yaw);
		serializer.writeFloat(0);
		serializer.writeFloat(pitch);
		serializer.writeItemStack(item);
		serializer.writeBytes(PEDataWatcherSerializer.encode(meta));
		return this;
	}

}
