package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;

public abstract class MiddleEntityEffectAdd extends MiddleEntity {

	public MiddleEntityEffectAdd(MiddlePacketInit init) {
		super(init);
	}

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected int flags;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		effectId = serverdata.readByte();
		amplifier = serverdata.readByte();
		duration = VarNumberSerializer.readVarInt(serverdata);
		flags = serverdata.readByte();
	}

	@Override
	protected void handle() {
		if (GenericIdSkipper.EFFECT.getTable(version).isSet(effectId)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
