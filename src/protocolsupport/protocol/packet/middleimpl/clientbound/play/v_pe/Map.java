package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap {
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (this.columns == 0) { // For now we are only going to send texture updates
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA, connection.getVersion());

		VarNumberSerializer.writeSVarLong(serializer, this.itemData);

		// 0x08 = entity update
		// 0x04 = decoration update
		// 0x02 = texture update
		VarNumberSerializer.writeVarInt(serializer, 2); // texture update
		serializer.writeByte(this.scale); // scale
		VarNumberSerializer.writeSVarInt(serializer, this.columns); // columns
		VarNumberSerializer.writeSVarInt(serializer, this.rows); // row
		VarNumberSerializer.writeSVarInt(serializer, this.xstart); // offset X
		VarNumberSerializer.writeSVarInt(serializer, this.zstart); // offset Y
		VarNumberSerializer.writeVarInt(serializer, this.columns * this.rows);

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

		return RecyclableSingletonList.create(serializer);
	}
}
