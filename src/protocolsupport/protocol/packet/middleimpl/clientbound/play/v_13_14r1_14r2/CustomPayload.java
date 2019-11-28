package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(create(tag, data));
	}

	public static ClientBoundPacketData create(String tag, ByteBuf data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

}
