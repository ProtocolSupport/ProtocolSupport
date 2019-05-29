package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateStructureBlock extends ServerBoundMiddlePacket {

	public MiddleUpdateStructureBlock(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected Action action;
	protected Mode mode;
	protected String name;
	protected byte offsetX;
	protected byte offsetY;
	protected byte offsetZ;
	protected byte sizeX;
	protected byte sizeY;
	protected byte sizeZ;
	protected Mirror mirror;
	protected Rotation rotation;
	protected String metadata;
	protected float integrity;
	protected long seed;
	protected byte flags;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, action, mode, name, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, mirror, rotation, metadata, integrity, seed, flags));
	}

	public static ServerBoundPacketData create(Position position, Action action, Mode mode, String name,
		byte offsetX, byte offsetY, byte offsetZ, byte sizeX, byte sizeY, byte sizeZ,
		Mirror mirror, Rotation rotation, String metadata, float integrity, long seed, byte flags
	) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_STRUCTURE_BLOCK);
		PositionSerializer.writePosition(creator, position);
		MiscSerializer.writeVarIntEnum(creator, action);
		MiscSerializer.writeVarIntEnum(creator, mode);
		StringSerializer.writeVarIntUTF8String(creator, name);
		creator.writeByte(offsetX);
		creator.writeByte(offsetY);
		creator.writeByte(offsetZ);
		creator.writeByte(sizeX);
		creator.writeByte(sizeY);
		creator.writeByte(sizeZ);
		MiscSerializer.writeVarIntEnum(creator, mirror);
		MiscSerializer.writeVarIntEnum(creator, rotation);
		StringSerializer.writeVarIntUTF8String(creator, metadata);
		creator.writeFloat(integrity);
		VarNumberSerializer.writeVarLong(creator, seed);
		creator.writeByte(flags);
		return creator;
	}

	public enum Action {
		UPDATE, SAVE, LOAD, DETECT;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

	public enum Mode {
		SAVE, LOAD, CORNER, DATA;
		public static final EnumConstantLookups.EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Mode.class);
	}

	public enum Mirror {
		NONE, LEFT_RIGHT, FRONT_BACK;
		public static final EnumConstantLookups.EnumConstantLookup<Mirror> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Mirror.class);
	}

	public enum Rotation {
		NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90;
		public static final EnumConstantLookups.EnumConstantLookup<Rotation> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Rotation.class);
	}

}
