package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {


	public MiddleBlockTileUpdate(MiddlePacketInit init) {
		super(init);
	}

	protected TileEntity tile;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		int type = serverdata.readUnsignedByte();
		NBTCompound tag = ItemStackSerializer.readDirectTag(serverdata);
		tile = new TileEntity(TileEntityType.getByNetworkId(type), position, tag);
	}

}
