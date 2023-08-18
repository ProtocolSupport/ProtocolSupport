package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatMessage;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class ChatMessage extends MiddleChatMessage implements
IServerboundMiddlePacketV20 {

	public ChatMessage(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		message = StringCodec.readVarIntUTF8String(clientdata, 256);
		timestamp = clientdata.readLong();
		salt = clientdata.readLong();
		signature = OptionalCodec.readOptional(clientdata, signatureData -> BytesCodec.readBytes(signatureData, 256));
		messageCount = VarNumberCodec.readVarInt(clientdata);
		ack = MiscDataCodec.readFixedBitSet(clientdata, 20);
	}

}
