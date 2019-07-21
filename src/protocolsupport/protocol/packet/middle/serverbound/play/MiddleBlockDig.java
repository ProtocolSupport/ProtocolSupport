package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockDig extends ServerBoundMiddlePacket {

	public MiddleBlockDig(ConnectionImpl connection) {
		super(connection);
	}

	protected Action status;
	protected final Position position = new Position(0, 0, 0);
	protected int face;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(status, position, face));
	}

	public static ServerBoundPacketData create(Action status, Position position, int face) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_BLOCK_DIG);
		MiscSerializer.writeVarIntEnum(creator, status);
		PositionSerializer.writePosition(creator, position);
		creator.writeByte(face);
		return creator;
	}

	protected static enum Action {
		START_DIG, CANCEL_DIG, FINISH_DIG, DROP_ITEM_ALL, DROP_ITEM_SINGLE, FINISH_USE, SWAP_ITEMS;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
