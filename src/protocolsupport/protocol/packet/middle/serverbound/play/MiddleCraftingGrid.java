package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;


public abstract class MiddleCraftingGrid extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int actionNumber;
	protected Entry[] returnEntries;
	protected Entry[] prepareEntries;

	private static final BiConsumer<ByteBuf, Entry> entryWriter = (to, entry) -> {
		ItemStackSerializer.writeItemStack(to, ProtocolVersionsHelper.LATEST_PC, entry.itemstack, false);
		to.writeByte(entry.craftSlot);
		to.writeByte(entry.playerSlot);
	};

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_PREPARE_CRAFTING_GRID);
		creator.writeByte(windowId);
		creator.writeShort(actionNumber);
		ArraySerializer.writeShortTArray(creator, returnEntries, entryWriter);
		ArraySerializer.writeShortTArray(creator, prepareEntries, entryWriter);
		return RecyclableSingletonList.create(creator);
	}

	protected static class Entry {
		protected final ItemStackWrapper itemstack;
		protected final int craftSlot;
		protected final int playerSlot;
		public Entry(ItemStackWrapper itemstack, int craftSlot, int playerSlot) {
			this.itemstack = itemstack;
			this.craftSlot = craftSlot;
			this.playerSlot = playerSlot;
		}
	}

}
