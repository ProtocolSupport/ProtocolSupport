package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map extends MiddleMap {
	List<String> sendMaps = new ArrayList<String>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (sendMaps.contains(String.valueOf(this.itemData))) {
			return RecyclableEmptyList.get();
		}
		System.out.println("Sending map data for " + this.itemData + "... scale: " + this.scale + ", col: " + this.columns + ", rows: " + this.rows + ", xstart: " + this.xstart + ", zstart: " + this.zstart);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA, connection.getVersion());

		VarNumberSerializer.writeSVarLong(serializer, this.itemData);

		// 0x08 = entity update
		// 0x04 = decoration update
		// 0x02 = texture update
		// VarNumberSerializer.writeVarInt(serializer, 0x00);

		VarNumberSerializer.writeVarInt(serializer, 2); // texture update
		serializer.writeByte(this.scale); // scale
		VarNumberSerializer.writeSVarInt(serializer, this.columns); // columns
		VarNumberSerializer.writeSVarInt(serializer, this.rows); // row
		VarNumberSerializer.writeSVarInt(serializer, this.xstart); // offset X
		VarNumberSerializer.writeSVarInt(serializer, this.zstart); // offset Y
		VarNumberSerializer.writeVarInt(serializer, this.columns*this.rows);

		RemappingTable.ArrayBasedIdRemappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(ProtocolVersion.MINECRAFT_PE);

		// data
		for (byte byteColor : this.colors) {
			try {
				int pocketId = colorRemapper.getRemap(byteColor);
				VarNumberSerializer.writeVarInt(serializer, pocketId);
			} catch (Exception e) {
				VarNumberSerializer.writeVarInt(serializer, 0x000000);
			}
		}

		sendMaps.add(String.valueOf(this.itemData));
		return RecyclableSingletonList.create(serializer);
	}
}
