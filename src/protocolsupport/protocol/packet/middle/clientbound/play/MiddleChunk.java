package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public abstract class MiddleChunk extends ClientBoundMiddlePacket {

	public MiddleChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;
	protected boolean full;
	protected int bitmask;
	protected ByteBuf data;
	protected TileEntity[] tiles;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readChunkCoord(serverdata);
		full = serverdata.readBoolean();
		bitmask = VarNumberSerializer.readVarInt(serverdata);
		data = ArraySerializer.readVarIntByteArraySlice(serverdata);
		tiles = ArraySerializer.readVarIntTArray(
			serverdata, TileEntity.class,
			from -> {
				NBTCompound tag = ItemStackSerializer.readTag(from);
				return new TileEntity(tag);
			}
		);
	}

}
