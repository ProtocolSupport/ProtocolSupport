package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleEntityEquipment extends ClientBoundMiddlePacket {

	public MiddleEntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected Slot slot;
	protected NetworkItemStack itemstack;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		slot = MiscSerializer.readVarIntEnum(serverdata, Slot.CONSTANT_LOOKUP);
		itemstack = ItemStackSerializer.readItemStack(serverdata);
	}

	public static enum Slot {
		MAIN_HAND, OFF_HAND,
		BOOTS, LEGGINGS, CHESTPLATE, HELMET;

		public static final EnumConstantLookup<Slot> CONSTANT_LOOKUP = new EnumConstantLookup<>(Slot.class);
	}

}
