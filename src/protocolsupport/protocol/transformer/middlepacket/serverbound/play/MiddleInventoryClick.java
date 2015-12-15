package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int slot;
	protected int button;
	protected int actionNumber;
	protected int mode;
	protected ItemStack itemstack;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_WINDOW_CLICK.get());
		creator.writeByte(windowId);
		creator.writeShort(slot);
		creator.writeByte(button);
		creator.writeShort(actionNumber);
		creator.writeByte(mode);
		creator.writeItemStack(itemstack);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
