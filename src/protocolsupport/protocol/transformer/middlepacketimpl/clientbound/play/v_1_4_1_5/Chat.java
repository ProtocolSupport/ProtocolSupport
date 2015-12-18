package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChat;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class Chat extends MiddleChat<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(LegacyUtils.toText(ChatSerializer.a(chatJson)));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CHAT_ID, serializer));
	}

}
