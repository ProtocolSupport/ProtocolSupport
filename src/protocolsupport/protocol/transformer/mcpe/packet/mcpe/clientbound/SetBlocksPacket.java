package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SetBlocksPacket implements ClientboundPEPacket {

	public final static byte FLAG_NONE = (byte) 0b0000;
	public final static byte FLAG_NEIGHBORS = (byte) 0b0001;
	public final static byte FLAG_NETWORK = (byte) 0b0010;
	public final static byte FLAG_NOGRAPHIC = (byte) 0b0100;
	public final static byte FLAG_PRIORITY = (byte) 0b1000;
	public final static byte FLAG_ALL = (byte) (FLAG_NEIGHBORS | FLAG_NETWORK);
	public final static byte FLAG_ALL_PRIORITY = (byte) (FLAG_ALL | FLAG_PRIORITY);

	protected UpdateBlockRecord[] records;

	public SetBlocksPacket(UpdateBlockRecord... records) {
		this.records = records;
	}

	@Override
	public int getId() {
		return PEPacketIDs.UPDATE_BLOCK_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(records.length);
		for (UpdateBlockRecord record : records) {
			buf.writeInt(record.x);
			buf.writeInt(record.z);
			buf.writeByte(record.y);
			buf.writeByte(record.block);
			buf.writeByte((record.flags << 4) | record.meta);
		}
		return this;
	}

	public static class UpdateBlockRecord {
		protected int x;
		protected int z;
		protected byte y;
		protected byte block;
		protected byte meta;
		protected byte flags;

		public UpdateBlockRecord(int x, int y, int z, int block, int meta, byte flags) {
			this.x = x;
			this.y = (byte) y;
			this.z = z;
			this.block = (byte) block;
			this.meta = (byte) meta;
			this.flags = flags;
		}

	}

}
