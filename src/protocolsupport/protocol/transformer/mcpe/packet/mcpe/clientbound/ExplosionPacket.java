package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class ExplosionPacket implements ClientboundPEPacket {

	protected float x;
	protected float y;
	protected float z;
	protected float radius;
	protected AffectedPosition[] affectedBlocks;

	public ExplosionPacket(float x, float y, float z, float radius, AffectedPosition[] affectedBlocks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.affectedBlocks = affectedBlocks;
	}

	@Override
	public int getId() {
		return PEPacketIDs.EXPLODE_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(radius);
		buf.writeInt(affectedBlocks.length);
		for (AffectedPosition apos : affectedBlocks) {
			buf.writeByte(apos.x);
			buf.writeByte(apos.y);
			buf.writeByte(apos.z);
		}
		return this;
	}

	public static class AffectedPosition {
		protected byte x;
		protected byte y;
		protected byte z;

		public AffectedPosition(byte x, byte y, byte z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

}
