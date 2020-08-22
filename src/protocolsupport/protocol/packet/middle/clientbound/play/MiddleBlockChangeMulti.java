package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	public MiddleBlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	protected long chunkCoordWithSection;
	protected boolean large; //TODO: figure out what exactly this does, currently set to true if there is more than 64 dirty blocks in a chunk
	protected long[] records;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		chunkCoordWithSection = serverdata.readLong();
		large = serverdata.readBoolean();
		records = ArraySerializer.readVarIntVarLongArray(serverdata);
	}

	protected static int getChunkX(long coord) {
		return (int) (coord >> 42);
	}

	protected static int getChunkZ(long coord) {
		return (int) ((coord << 22) >> 42);
	}

	protected static int getChunkSectionY(long coord) {
		return (int) ((coord << 44) >> 44);
	}

	protected static int getRecordBlockData(long record) {
		return (int) (record >>> 12);
	}

	protected static int getRecordRelX(long record) {
		return (int) ((record >> 8) & 0xF);
	}

	protected static int getRecordRelZ(long record) {
		return (int) ((record >> 4) & 0xF);
	}

	protected static int getRecordRelY(long record) {
		return (int) (record & 0xF);
	}

}
