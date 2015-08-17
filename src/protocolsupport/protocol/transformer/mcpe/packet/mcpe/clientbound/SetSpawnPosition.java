package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import org.bukkit.Location;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SetSpawnPosition implements ClientboundPEPacket {

	protected float x;
	protected float y;
	protected float z;

	public SetSpawnPosition(Location playerLocation) {
		this.x = (float) playerLocation.getX();
		this.y = (float) playerLocation.getY();
		this.z = (float) playerLocation.getZ();
	}

	@Override
	public int getId() {
		return PEPacketIDs.SET_SPAWN_POSITION_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		return this;
	}

}
