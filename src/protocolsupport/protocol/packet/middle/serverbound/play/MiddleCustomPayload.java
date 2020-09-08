package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	public MiddleCustomPayload(MiddlePacketInit init) {
		super(init);
	}

	protected String tag;
	protected ByteBuf data;

	@Override
	protected void writeToServer() {
		codec.read(create(tag, data));
	}

	public static ServerBoundPacketData create(String tag) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		return serializer;
	}

	public static ServerBoundPacketData create(String tag, byte[] data) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

	public static ServerBoundPacketData create(String tag, ByteBuf data) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

	public static ServerBoundPacketData create(String tag, Consumer<ByteBuf> dataWriter) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		dataWriter.accept(serializer);
		return serializer;
	}

}
