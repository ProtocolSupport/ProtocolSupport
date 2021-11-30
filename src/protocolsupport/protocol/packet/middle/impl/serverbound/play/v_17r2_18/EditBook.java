package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_17r2_18;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV18;

public class EditBook extends MiddleEditBook implements
IServerboundMiddlePacketV17r2,
IServerboundMiddlePacketV18 {

	public EditBook(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		slot = VarNumberCodec.readVarInt(clientdata);
		pages = ArrayCodec.readVarIntVarIntUTF8StringArray(clientdata);
		title = clientdata.readBoolean() ? StringCodec.readVarIntUTF8String(clientdata) : null;
	}

}
