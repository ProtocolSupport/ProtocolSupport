package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.PEDataWatcherSerializer;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherObject.ValueType;

public class SpawnPlayerPacket implements ClientboundPEPacket {

	protected UUID uuid;
	protected String name;
	protected int entityId;
	protected float locX;
	protected float locY;
	protected float locZ;
	protected float yaw;
	protected float pitch;
	protected ItemStack item;

	public SpawnPlayerPacket(UUID uuid, String name, int entityId, float locX, float locY, float locZ, float yaw, float pitch) {
		this.uuid = uuid;
		this.name = name;
		this.entityId = entityId;
		this.locX = locX;
		this.locY = locY;
		this.locZ = locZ;
		this.yaw = yaw;
		this.pitch = pitch;
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
		//TODO: actual metadata
		TIntObjectHashMap<DataWatcherObject> metadata = new TIntObjectHashMap<>();
		metadata.put(0, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(1, new DataWatcherObject(ValueType.SHORT, (short) 300));
		metadata.put(2, new DataWatcherObject(ValueType.STRING, name));
		metadata.put(3, new DataWatcherObject(ValueType.BYTE, (byte) 1));
		metadata.put(4, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(7, new DataWatcherObject(ValueType.INT, 0));
		metadata.put(8, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(15, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(16, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(17, new DataWatcherObject(ValueType.VECTOR3I, new BlockPosition(0, 0, 0)));
		serializer.writeBytes(PEDataWatcherSerializer.encode(metadata));
		return this;
	}

}
