package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatCommand;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class ChatCommand extends MiddleChatCommand implements
IServerboundMiddlePacketV20 {

	public ChatCommand(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		command = StringCodec.readVarIntUTF8String(clientdata, 256);
		timestamp = clientdata.readLong();
		salt = clientdata.readLong();
		signatures = ArrayCodec.readVarIntTArray(clientdata, ArgumentSignature.class, signatureData -> {
			String name = StringCodec.readVarIntUTF8String(signatureData, 16);
			byte[] signature = BytesCodec.readBytes(signatureData, 256);
			return new ArgumentSignature(name, signature);
		});
		messageCount = VarNumberCodec.readVarInt(clientdata);
		ack = MiscDataCodec.readFixedBitSet(clientdata, 20);
	}

}
