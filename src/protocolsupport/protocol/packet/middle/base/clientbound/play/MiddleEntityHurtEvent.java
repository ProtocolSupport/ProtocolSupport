package protocolsupport.protocol.packet.middle.base.clientbound.play;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;

public abstract class MiddleEntityHurtEvent extends MiddleEntityData {

	protected MiddleEntityHurtEvent(IMiddlePacketInit init) {
		super(init);
	}

	protected int damageTypeId;
	protected int causeEntityId;
	protected int damagerEntityId;
	protected Vector location;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		damageTypeId = VarNumberCodec.readVarInt(serverdata);
		causeEntityId = VarNumberCodec.readVarInt(serverdata);
		damagerEntityId = VarNumberCodec.readVarInt(serverdata);
		location = OptionalCodec.readOptional(serverdata, locationData -> new Vector(serverdata.readDouble(), serverdata.readDouble(), serverdata.readDouble()));
	}

}
