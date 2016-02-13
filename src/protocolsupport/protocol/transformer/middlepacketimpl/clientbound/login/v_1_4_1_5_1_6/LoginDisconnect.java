package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_4_1_5_1_6;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginDisconnect extends MiddleLoginDisconnect<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.LOGIN_DISCONNECT_ID, version);
		serializer.writeString(LegacyUtils.toText(ChatSerializer.a(messageJson)));
		return RecyclableSingletonList.create(serializer);
	}

}
