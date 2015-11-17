package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class AttachEntityPacket implements ClientboundPEPacket {

	protected int vehicle;
	protected int rider;
	protected boolean attach;

	public AttachEntityPacket(int rider, int ridden, boolean attach) {
		this.vehicle = rider;
		this.rider = ridden;
		this.attach = attach;
	}

	@Override
	public int getId() {
		return PEPacketIDs.SET_ENTITY_LINK_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(vehicle);
		buf.writeLong(rider);
		buf.writeBoolean(attach);
		return this;
	}

}
