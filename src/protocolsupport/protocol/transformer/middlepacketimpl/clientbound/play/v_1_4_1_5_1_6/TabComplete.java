package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class TabComplete extends MiddleTabComplete<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		if (matches.length == 0) {
			return Collections.emptyList();
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(Utils.clampString(StringUtils.join(matches, '\u0000'), Short.MAX_VALUE));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_TAB_COMPLETE_ID, serializer));
	}

}
