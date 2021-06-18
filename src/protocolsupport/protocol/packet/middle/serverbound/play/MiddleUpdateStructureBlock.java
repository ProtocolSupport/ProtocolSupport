package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleUpdateStructureBlock extends ServerBoundMiddlePacket {

	protected MiddleUpdateStructureBlock(MiddlePacketInit init) {
		super(init);
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
	protected void write() {
		codec.writeServerbound(create(position, action, mode, name, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, mirror, rotation, metadata, integrity, seed, flags));
	}

	public static ServerBoundPacketData create(Position position, Action action, Mode mode, String name,
		byte offsetX, byte offsetY, byte offsetZ, byte sizeX, byte sizeY, byte sizeZ,
		Mirror mirror, Rotation rotation, String metadata, float integrity, long seed, byte flags
	) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_UPDATE_STRUCTURE_BLOCK);
		PositionCodec.writePosition(creator, position);
		MiscDataCodec.writeVarIntEnum(creator, action);
		MiscDataCodec.writeVarIntEnum(creator, mode);
		StringCodec.writeVarIntUTF8String(creator, name);
		creator.writeByte(offsetX);
		creator.writeByte(offsetY);
		creator.writeByte(offsetZ);
		creator.writeByte(sizeX);
		creator.writeByte(sizeY);
		creator.writeByte(sizeZ);
		MiscDataCodec.writeVarIntEnum(creator, mirror);
		MiscDataCodec.writeVarIntEnum(creator, rotation);
		StringCodec.writeVarIntUTF8String(creator, metadata);
		creator.writeFloat(integrity);
		VarNumberCodec.writeVarLong(creator, seed);
		creator.writeByte(flags);
		return creator;
	}

	public enum Action {
		UPDATE, SAVE, LOAD, DETECT;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

	public enum Mode {
		SAVE, LOAD, CORNER, DATA;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	public enum Mirror {
		NONE, LEFT_RIGHT, FRONT_BACK;
		public static final EnumConstantLookup<Mirror> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mirror.class);
	}

	public enum Rotation {
		NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90;
		public static final EnumConstantLookup<Rotation> CONSTANT_LOOKUP = new EnumConstantLookup<>(Rotation.class);
	}

}
