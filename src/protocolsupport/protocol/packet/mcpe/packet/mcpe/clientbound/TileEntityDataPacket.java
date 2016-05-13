package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_9_R2.NBTTagCompound;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.packet.mcpe.utils.TileEntityUtils;

public class TileEntityDataPacket implements ClientboundPEPacket {

	protected int x;
	protected int y;
	protected int z;
	protected NBTTagCompound tag;

	public TileEntityDataPacket(int x, int y, int z, NBTTagCompound tag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tag = tag;
	}

	@Override
	public int getId() {
		return PEPacketIDs.TILE_ENTITY_DATA_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBytes(TileEntityUtils.toNoLengthPrefixBuf(tag));
		return this;
	}

}
