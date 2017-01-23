package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class MiddleChunk<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;
	protected boolean full;
	protected int bitmask;
	protected byte[] data;
	protected NBTTagCompoundWrapper[] tiles;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		chunkX = serializer.readInt();
		chunkZ = serializer.readInt();
		full = serializer.readBoolean();
		bitmask = serializer.readVarInt();
		data = serializer.readByteArray();
		tiles = new NBTTagCompoundWrapper[serializer.readVarInt()];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = serializer.readTag();
		}
	}

}
