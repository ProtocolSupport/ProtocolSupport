package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.TileEntityUtils;

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
