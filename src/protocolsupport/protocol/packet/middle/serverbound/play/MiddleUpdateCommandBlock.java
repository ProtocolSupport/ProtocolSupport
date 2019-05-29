package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateCommandBlock extends ServerBoundMiddlePacket {

	public static final int FLAG_TRACK_OUTPUT = 0x1;
	public static final int FLAG_CONDITIONAL = 0x2;
	public static final int FLAG_AUTO = 0x4;

	public MiddleUpdateCommandBlock(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String command;
	protected Mode mode;
	protected int flags;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, command, mode, flags));
	}

	public static enum Mode {
		SEQUENCE, AUTO, REDSTONE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	public static ServerBoundPacketData create(Position position, String command, Mode mode, int flags) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_COMMAND_BLOCK);
		PositionSerializer.writePosition(creator, position);
		StringSerializer.writeVarIntUTF8String(creator, command);
		MiscSerializer.writeVarIntEnum(creator, mode);
		creator.writeByte(flags);
		return creator;
	}

}
