package protocolsupport.protocol.packet.middle.base.serverbound.play;

import java.util.BitSet;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleChatCommand extends ServerBoundMiddlePacket {

	protected MiddleChatCommand(IMiddlePacketInit init) {
		super(init);
	}

	protected String command;
	protected long timestamp;
	protected long salt;
	protected ArgumentSignature[] signatures;
	protected int messageCount;
	protected BitSet ack;

	@Override
	protected void write() {
		io.writeServerbound(create(command, timestamp, salt, signatures, messageCount, ack));
	}

	public static ServerBoundPacketData create(String message, long timestamp, long salt, ArgumentSignature[] signatures, int messageCount, BitSet ack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT_COMMAND);
		StringCodec.writeVarIntUTF8String(creator, message);
		creator.writeLong(timestamp);
		creator.writeLong(salt);
		ArrayCodec.writeVarIntTArray(creator, signatures, (argumentData, argument) -> {
			if (argument.signature().length != 256) {
				throw new IllegalArgumentException("Invalid argument " + argument.name() + " signature length, expected 256, got " + argument.signature().length);
			}
			StringCodec.writeVarIntUTF8String(argumentData, argument.name());
			argumentData.writeBytes(argument.signature());
		});
		VarNumberCodec.writeVarLong(creator, messageCount);
		MiscDataCodec.writeFixedBitSet(creator, ack, 20);
		return creator;
	}

	public static record ArgumentSignature(String name, byte[] signature) {
		public ArgumentSignature(String name, byte[] signature) {
			this.name = name;
			this.signature = signature;
		}
	}

}
