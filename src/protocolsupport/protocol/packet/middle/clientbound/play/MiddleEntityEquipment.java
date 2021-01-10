package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleEntityEquipment extends ClientBoundMiddlePacket {

	public MiddleEntityEquipment(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected final ArrayList<Entry> entries = new ArrayList<>(Slot.CONSTANT_LOOKUP.getCount());

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		byte slotId;
		do {
			slotId = serverdata.readByte();
			entries.add(new Entry(Slot.CONSTANT_LOOKUP.getByOrdinal(slotId & Byte.MAX_VALUE), ItemStackSerializer.readItemStack(serverdata)));
		} while (slotId < 0);
	}

	@Override
	protected void cleanup() {
		entries.clear();
	}

	public static class Entry {

		protected final Slot slot;
		protected final NetworkItemStack itemstack;

		public Entry(Slot slot, NetworkItemStack itemstack) {
			this.slot = slot;
			this.itemstack = itemstack;
		}

		public Slot getSlot() {
			return slot;
		}

		public NetworkItemStack getItemStack() {
			return itemstack;
		}

	}

	public static enum Slot {
		MAIN_HAND, OFF_HAND,
		BOOTS, LEGGINGS, CHESTPLATE, HELMET;

		public static final EnumConstantLookup<Slot> CONSTANT_LOOKUP = new EnumConstantLookup<>(Slot.class);
	}

}
