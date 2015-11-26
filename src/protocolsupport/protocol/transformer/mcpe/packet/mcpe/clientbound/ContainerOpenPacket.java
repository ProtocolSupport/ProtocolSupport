package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class ContainerOpenPacket implements ClientboundPEPacket {

	protected int windowId;
	protected int type;
	protected int slots;
	protected int x;
	protected int y;
	protected int z;

	public ContainerOpenPacket(int windowId, int type, int slots, int x, int y, int z) {
		this.windowId = windowId;
		this.type = type;
		this.slots = slots;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getId() {
		return PEPacketIDs.CONTAINER_OPEN_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeByte(windowId);
		buf.writeByte(type);
		buf.writeShort(slots);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		return this;
	}

}
