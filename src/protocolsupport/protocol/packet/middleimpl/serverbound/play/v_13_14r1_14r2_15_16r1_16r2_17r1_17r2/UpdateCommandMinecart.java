package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandMinecart;

public class UpdateCommandMinecart extends MiddleUpdateCommandMinecart {

	public UpdateCommandMinecart(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = VarNumberCodec.readVarInt(clientdata);
		command = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		trackOutput = clientdata.readBoolean();
	}

}
