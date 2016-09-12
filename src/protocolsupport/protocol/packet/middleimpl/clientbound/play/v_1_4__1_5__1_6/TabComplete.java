package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TabComplete extends MiddleTabComplete<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (matches.length == 0) {
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_TAB_COMPLETE_ID, version);
		serializer.writeString(Utils.clampString(StringUtils.join(matches, '\u0000'), Short.MAX_VALUE));
		return RecyclableSingletonList.create(serializer);
	}

}
