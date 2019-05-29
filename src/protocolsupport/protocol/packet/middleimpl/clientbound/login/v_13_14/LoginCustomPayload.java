package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginCustomPayload extends MiddleLoginCustomPayload {

	public LoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(serializer, id);
		StringSerializer.writeString(serializer, version, tag);
		serializer.writeBytes(data);
		return RecyclableSingletonList.create(serializer);
	}

}
