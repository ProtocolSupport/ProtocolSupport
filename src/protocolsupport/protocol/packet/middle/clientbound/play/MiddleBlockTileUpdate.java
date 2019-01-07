package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	public MiddleBlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected TileEntity tile;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		int type = serverdata.readUnsignedByte();
		NBTCompound tag = ItemStackSerializer.readTag(serverdata);
		tile = new TileEntity(TileEntityType.getByNetworkId(type), position, tag);
	}

}
