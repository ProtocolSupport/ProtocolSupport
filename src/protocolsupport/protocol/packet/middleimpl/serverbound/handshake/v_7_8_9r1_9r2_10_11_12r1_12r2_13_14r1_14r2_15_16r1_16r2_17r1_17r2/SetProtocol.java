package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.handshake.MiddleSetProtocol;

public class SetProtocol extends MiddleSetProtocol {

	public SetProtocol(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		VarNumberCodec.readVarInt(clientdata);
		hostname = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		port = clientdata.readUnsignedShort();
		nextState = VarNumberCodec.readVarInt(clientdata);
	}

}
