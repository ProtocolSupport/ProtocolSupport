package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateCommandBlock extends ServerBoundMiddlePacket {

	public static final int FLAGS_BIT_TRACK_OUTPUT = 0;
	public static final int FLAGS_BIT_CONDITIONAL = 1;
	public static final int FLAGS_BIT_AUTO = 2;

	public MiddleUpdateCommandBlock(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String command;
	protected Mode mode;
	protected int flags;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, command, mode, flags));
	}

	public static enum Mode {
		SEQUENCE, AUTO, REDSTONE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	public static ServerBoundPacketData create(Position position, String command, Mode mode, int flags) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_UPDATE_COMMAND_BLOCK);
		PositionSerializer.writePosition(creator, position);
		StringSerializer.writeVarIntUTF8String(creator, command);
		MiscSerializer.writeVarIntEnum(creator, mode);
		creator.writeByte(flags);
		return creator;
	}

}
