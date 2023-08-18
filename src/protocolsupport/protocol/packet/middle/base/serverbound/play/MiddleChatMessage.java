package protocolsupport.protocol.packet.middle.base.serverbound.play;

import java.util.BitSet;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleChatMessage extends ServerBoundMiddlePacket {

	protected MiddleChatMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected String message;
	protected long timestamp;
	protected long salt;
	protected byte[] signature;
	protected int messageCount;
	protected BitSet ack;

	@Override
	protected void write() {
		io.writeServerbound(create(message, timestamp, salt, signature, messageCount, ack));
	}

	public static ServerBoundPacketData create(String message, long timestamp, long salt, byte[] signature, int messageCount, BitSet ack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT_MESSAGE);
		StringCodec.writeVarIntUTF8String(creator, message);
		creator.writeLong(timestamp);
		creator.writeLong(salt);
		OptionalCodec.writeOptional(creator, signature, (signatureData, signatureValue) -> {
			if (signatureValue.length != 256) {
				throw new IllegalArgumentException("Invalid message signature length, expected 256, got " + signatureValue.length);
			}
			creator.writeBytes(signatureValue);
		});
		VarNumberCodec.writeVarLong(creator, messageCount);
		MiscDataCodec.writeFixedBitSet(creator, ack, 20);
		return creator;
	}

}
