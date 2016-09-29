package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_MAP_ID, version);
		serializer.writeVarInt(itemData);
		serializer.writeByte(scale);
		serializer.writeVarInt(icons.length);
		for (Icon icon : icons) {
			serializer.writeByte(icon.dirtype);
			serializer.writeByte(icon.x);
			serializer.writeByte(icon.z);
		}
		serializer.writeByte(columns);
		if (columns > 0) {
			serializer.writeByte(rows);
			serializer.writeByte(xstart);
			serializer.writeByte(zstart);
			serializer.writeByteArray(data);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
