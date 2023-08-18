package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.SoundCategory;

public abstract class MiddleWorldSound extends ClientBoundMiddlePacket {

	protected MiddleWorldSound(IMiddlePacketInit init) {
		super(init);
	}

	protected Integer id;
	protected String name;
	protected Float fixedrange;
	protected SoundCategory category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;
	protected long seed;

	@Override
	protected void decode(ByteBuf serverdata) {
		int encodedId = VarNumberCodec.readVarInt(serverdata);
		if (encodedId != 0) {
			id = encodedId - 1;
			name = null;
		} else {
			id = null;
			name = StringCodec.readVarIntUTF8String(serverdata);
			fixedrange = OptionalCodec.readOptional(serverdata, ByteBuf::readFloat);
		}
		category = MiscDataCodec.readVarIntEnum(serverdata, SoundCategory.CONSTANT_LOOKUP);
		x = serverdata.readInt();
		y = serverdata.readInt();
		z = serverdata.readInt();
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
		seed = serverdata.readLong();
	}

}
