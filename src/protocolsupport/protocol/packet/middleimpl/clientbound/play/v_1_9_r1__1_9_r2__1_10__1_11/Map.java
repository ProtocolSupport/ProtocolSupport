package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11;

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
		VarNumberSerializer.writeVarInt(serializer, icons.length);
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
			ArraySerializer.writeByteArray(serializer, version, data);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
