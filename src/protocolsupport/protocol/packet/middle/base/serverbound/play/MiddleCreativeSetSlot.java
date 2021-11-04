package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	protected MiddleCreativeSetSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	protected void write() {
		io.writeServerbound(create(slot, itemstack));
	}

	public static ServerBoundPacketData create(int slot, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CREATIVE_SET_SLOT);
		creator.writeShort(slot);
		ItemStackCodec.writeItemStack(creator, itemstack);
		return creator;
	}

}
