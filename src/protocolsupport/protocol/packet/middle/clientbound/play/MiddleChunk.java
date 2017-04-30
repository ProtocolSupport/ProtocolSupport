package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
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
		data = ArraySerializer.readByteArray(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
		tiles = ArraySerializer.readVarIntTArray(
			serverdata, NBTTagCompoundWrapper.class,
			(from) -> ItemStackSerializer.readTag(serverdata, ProtocolVersion.getLatest(ProtocolType.PC))
		);
	}

}
