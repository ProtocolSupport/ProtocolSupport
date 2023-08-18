package protocolsupport.protocol.packet.middle.impl.serverbound.login.v_19__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class LoginStart extends MiddleLoginStart implements
IServerboundMiddlePacketV20 {

	public LoginStart(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		name = StringCodec.readVarIntUTF8String(clientdata, 16);
		uuid = OptionalCodec.readOptional(clientdata, UUIDCodec::readUUID);
	}

}
