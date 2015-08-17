package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import org.bukkit.Location;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class StartGamePacket implements ClientboundPEPacket {

	protected int seed = 0;
	protected int generator = 1;
	protected int dimension;
	protected int gamemode;
	protected long eid;
	protected int spawnX;
	protected int spawnY;
	protected int spawnZ;
	protected float x;
	protected float y;
	protected float z;

	public StartGamePacket(int dimension, int gamemode, long eid, Location worldspawn, Location playerspawn) {
		this.dimension = dimension;
		this.gamemode = gamemode;
		this.eid = eid;
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
		buf.writeInt(seed);
		buf.writeByte(dimension);
		buf.writeInt(generator);
		buf.writeInt(gamemode);
		buf.writeLong(eid);
		buf.writeInt(spawnX);
		buf.writeInt(spawnY);
		buf.writeInt(spawnZ);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeBoolean(false);
		return this;
	}

}
