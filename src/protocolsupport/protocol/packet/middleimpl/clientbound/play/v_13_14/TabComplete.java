package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_TAB_COMPLETE_ID);
		VarNumberSerializer.writeVarInt(serializer, id);
		VarNumberSerializer.writeVarInt(serializer, start);
		VarNumberSerializer.writeVarInt(serializer, length);
		ArraySerializer.writeVarIntTArray(serializer, matches, (data, match) -> {
			StringSerializer.writeString(serializer, version, match.getMatch());
			serializer.writeBoolean(match.hasTooltip());
			if (match.hasTooltip()) {
				StringSerializer.writeString(serializer, version, match.getTooltip());
			}
		});
		return RecyclableSingletonList.create(serializer);
	}

}
