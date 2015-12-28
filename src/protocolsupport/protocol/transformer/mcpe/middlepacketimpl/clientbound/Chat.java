package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChat;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new ChatPacket(LegacyUtils.toText(ChatSerializer.a(chatJson))));
	}

}
