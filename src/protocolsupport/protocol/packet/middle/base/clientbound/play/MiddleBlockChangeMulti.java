package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	protected MiddleBlockChangeMulti(IMiddlePacketInit init) {
		super(init);
	}

	protected int chunkX;
	protected int chunkZ;
	protected int chunkSection;
	protected BlockChangeRecord[] records;

	@Override
	protected void decode(ByteBuf serverdata) {
		long chunkCoordWithSection = serverdata.readLong();
		chunkX = (int) (chunkCoordWithSection >> 42);
		chunkZ = (int) ((chunkCoordWithSection << 22) >> 42);
		chunkSection = (int) ((chunkCoordWithSection << 44) >> 44);
		records = ArrayCodec.readVarIntTArray(serverdata, BlockChangeRecord.class, recordFrom -> {
			long recordData = VarNumberCodec.readVarLong(recordFrom);
			return new BlockChangeRecord(
				(byte) ((recordData >> 8) & 0xF),
				(byte) ((recordData >> 4) & 0xF),
				(byte) (recordData & 0xF),
				(int) (recordData >>> 12)
			);
		});
	}

	protected static class BlockChangeRecord {

		protected final byte relX;
		protected final byte relY;
		protected final byte relZ;
		protected final int blockdata;

		public BlockChangeRecord(byte relX, byte relZ, byte relY, int blockdata) {
			this.relX = relX;
			this.relY = relY;
			this.relZ = relZ;
			this.blockdata = blockdata;
		}

		public byte getRelX() {
			return relX;
		}

		public byte getRelY() {
			return relY;
		}

		public byte getRelZ() {
			return relZ;
		}

		public int getBlockData() {
			return blockdata;
		}

	}

}
