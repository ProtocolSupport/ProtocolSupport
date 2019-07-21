package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
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
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE);
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
