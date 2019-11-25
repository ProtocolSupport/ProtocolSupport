package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	public MiddleCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected ByteBuf data;

	@Override
	public void writeToServer() {
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

}
