package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyUtils;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockSignUpdate;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockSignUpdate extends MiddleBlockSignUpdate<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_SIGN_ID, version);
		serializer.writeInt(position.getX());
		serializer.writeShort(position.getY());
		serializer.writeInt(position.getZ());
		for (String lineJson : linesJson) {
			serializer.writeString(Utils.clampString(LegacyUtils.toText(ChatAPI.fromJSON(lineJson)), 15));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
