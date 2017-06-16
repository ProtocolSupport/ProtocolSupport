package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_MAP_ID, version);
		VarNumberSerializer.writeVarInt(serializer, itemData);
		serializer.writeByte(scale);
		serializer.writeBoolean(showIcons);
		ArraySerializer.writeVarIntTArray(serializer, icons, (to, icon) -> {
			to.writeByte(icon.dirtype);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
		});
		serializer.writeByte(columns);
		if (columns > 0) {
			serializer.writeByte(rows);
			serializer.writeByte(xstart);
			serializer.writeByte(zstart);
			ArraySerializer.writeByteArray(serializer, version, data);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
