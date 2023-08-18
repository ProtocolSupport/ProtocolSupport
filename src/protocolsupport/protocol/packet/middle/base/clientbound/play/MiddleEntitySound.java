package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.SoundCategory;

public abstract class MiddleEntitySound extends ClientBoundMiddlePacket {

	protected MiddleEntitySound(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected SoundCategory category;
	protected int entityId;
	protected float volume;
	protected float pitch;
	protected long seed;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = VarNumberCodec.readVarInt(serverdata);
		category = MiscDataCodec.readVarIntEnum(serverdata, SoundCategory.CONSTANT_LOOKUP);
		entityId = VarNumberCodec.readVarInt(serverdata);
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
		seed = serverdata.readLong();
	}

}
