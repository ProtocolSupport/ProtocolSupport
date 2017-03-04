package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class MiddleChunk extends ClientBoundMiddlePacket {

	protected int chunkX;
	protected int chunkZ;
	protected boolean full;
	protected int bitmask;
	protected byte[] data;
	protected NBTTagCompoundWrapper[] tiles;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkX = serverdata.readInt();
		chunkZ = serverdata.readInt();
		full = serverdata.readBoolean();
		bitmask = VarNumberSerializer.readVarInt(serverdata);
		data = ByteArraySerializer.readByteArray(serverdata, ProtocolVersion.getLatest());
		tiles = new NBTTagCompoundWrapper[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = ItemStackSerializer.readTag(serverdata, ProtocolVersion.getLatest());
		}
	}

}
