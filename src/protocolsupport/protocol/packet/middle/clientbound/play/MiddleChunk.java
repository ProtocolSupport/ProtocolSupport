package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class MiddleChunk extends ClientBoundMiddlePacket {

	public MiddleChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected int chunkX;
	protected int chunkZ;
	protected boolean full;
	protected int bitmask;
	protected ByteBuf data;
	protected NBTTagCompoundWrapper[] tiles;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkX = serverdata.readInt();
		chunkZ = serverdata.readInt();
		full = serverdata.readBoolean();
		bitmask = VarNumberSerializer.readVarInt(serverdata);
		data = ArraySerializer.readVarIntByteArraySlice(serverdata);
		tiles = ArraySerializer.readVarIntTArray(
			serverdata, NBTTagCompoundWrapper.class,
			from -> ItemStackSerializer.readTag(from, ProtocolVersionsHelper.LATEST_PC)
		);
	}

}
