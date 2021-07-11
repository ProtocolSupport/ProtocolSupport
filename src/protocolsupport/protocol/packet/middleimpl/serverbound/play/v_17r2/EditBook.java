package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;

public class EditBook extends MiddleEditBook {

	public EditBook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		slot = VarNumberCodec.readVarInt(clientdata);
		pages = ArrayCodec.readVarIntVarIntUTF8StringArray(clientdata);
		title = clientdata.readBoolean() ? StringCodec.readVarIntUTF8String(clientdata) : null;
	}

}
