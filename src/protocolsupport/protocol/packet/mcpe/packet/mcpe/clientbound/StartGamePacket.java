package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import org.bukkit.Location;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class StartGamePacket implements ClientboundPEPacket {

	protected int seed = 0;
	protected int generator = 1;
	protected int dimension;
	protected int gamemode;
	protected long entityId;
	protected int spawnX;
	protected int spawnY;
	protected int spawnZ;
	protected float x;
	protected float y;
	protected float z;
	protected boolean loadedInCreative;
	protected int dayLightCycleStopTime = -1;
	protected boolean isMinecraftEdu;
	protected String levelName = "Default";

	public StartGamePacket(int dimension, int gamemode, long eid, Location worldspawn, Location playerspawn) {
		this.dimension = dimension;
		this.gamemode = gamemode;
		this.entityId = eid;
		this.spawnX = worldspawn.getBlockX();
		this.spawnY = worldspawn.getBlockY();
		this.spawnZ = worldspawn.getBlockZ();
		this.x = (float) playerspawn.getX();
		this.y = (float) playerspawn.getY();
		this.z = (float) playerspawn.getZ();
	}

	@Override
	public int getId() {
		return PEPacketIDs.START_GAME_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeInt(seed);
		serializer.writeByte(0/*dimension*/);
		serializer.writeInt(generator);
		serializer.writeInt(gamemode);
		serializer.writeLong(entityId);
		serializer.writeInt(spawnX);
		serializer.writeInt(spawnY);
		serializer.writeInt(spawnZ);
		serializer.writeFloat(x);
		serializer.writeFloat(y + 1.63F);
		serializer.writeFloat(z);
		serializer.writeBoolean(loadedInCreative);
		serializer.writeByte(dayLightCycleStopTime);
		serializer.writeBoolean(isMinecraftEdu);
		serializer.writeString(levelName);
		return this;
	}

}
