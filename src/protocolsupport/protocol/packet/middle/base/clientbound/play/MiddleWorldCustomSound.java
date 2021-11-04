package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.SoundCategory;

public abstract class MiddleWorldCustomSound extends ClientBoundMiddlePacket {

	protected MiddleWorldCustomSound(IMiddlePacketInit init) {
		super(init);
	}

	protected String id;
	protected SoundCategory category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = StringCodec.readVarIntUTF8String(serverdata);
		category = MiscDataCodec.readVarIntEnum(serverdata, SoundCategory.CONSTANT_LOOKUP);
		x = serverdata.readInt();
		y = serverdata.readInt();
		z = serverdata.readInt();
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
