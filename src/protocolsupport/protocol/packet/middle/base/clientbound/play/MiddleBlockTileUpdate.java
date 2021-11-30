package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	protected MiddleBlockTileUpdate(IMiddlePacketInit init) {
		super(init);
	}

	protected TileEntity tile;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		TileEntityType type = TileEntityType.getByNetworkId(VarNumberCodec.readVarInt(serverdata));
		NBTCompound tag = ItemStackCodec.readDirectTag(serverdata);
		tile = new TileEntity(type, position, tag);
	}

}
