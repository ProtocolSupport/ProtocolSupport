package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.transformer.utils.ChunkTransformer;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleChunkMulti<T> extends ClientBoundMiddlePacket<T> {

	protected boolean hasSkyLight;
	protected int[] chunkX;
	protected int[] chunkZ;
	protected int[] bitmap;
	protected byte[][] data;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		hasSkyLight = serializer.readBoolean();
		int count = serializer.readVarInt();
		chunkX = new int[count];
		chunkZ = new int[count];
		bitmap = new int[count];
		data = new byte[count][];
		for (int i = 0; i < count; i++) {
			chunkX[i] = serializer.readInt();
			chunkZ[i] = serializer.readInt();
			bitmap[i] = serializer.readUnsignedShort();
		}
		for (int i = 0; i < count; i++) {
			data[i] = ChannelUtils.toArray(serializer.readBytes(ChunkTransformer.calcDataSize(Integer.bitCount(bitmap[i]), hasSkyLight, true)));
		}
	}

}
