package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;

public abstract class MiddleEntityEffectAdd extends MiddleEntity {

	public MiddleEntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected int flags;

	@Override
	public void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		effectId = serverdata.readByte();
		amplifier = serverdata.readByte();
		duration = VarNumberSerializer.readVarInt(serverdata);
		flags = serverdata.readByte();
	}

	@Override
	public void handleReadData() {
		if (GenericIdSkipper.EFFECT.getTable(version).shouldSkip(effectId)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
