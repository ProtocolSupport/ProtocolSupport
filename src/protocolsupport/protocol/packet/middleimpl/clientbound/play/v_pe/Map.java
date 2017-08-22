package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap {
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA, connection.getVersion());
		VarNumberSerializer.writeSVarLong(serializer, this.itemData);

		int flags = 0;
		//final int FLAG_ENTITY_UPDATE = 0x08;
		final int FLAG_DECORATION_UPDATE = 0x04;
		final int FLAG_TEXTURE_UPDATE = 0x02;
		final int FLAG_ITEM_UPDATE = 0x01;

		if (columns > 0) { flags |= FLAG_TEXTURE_UPDATE; }
		else if (icons.length > 0) { flags |= (FLAG_DECORATION_UPDATE | FLAG_ITEM_UPDATE) ; }

		VarNumberSerializer.writeVarInt(serializer, flags);

		//Implementation
		if ((flags & (FLAG_DECORATION_UPDATE | FLAG_TEXTURE_UPDATE)) != 0) {
			serializer.writeByte(scale);
		}

		if ((flags & FLAG_DECORATION_UPDATE) != 0) {
			VarNumberSerializer.writeVarInt(serializer, icons.length);
			for (Icon icon: icons) {
				serializer.writeByte(1); //type
				serializer.writeByte((byte) (icon.dirtype & 0xF0));
				serializer.writeByte(icon.x);
				serializer.writeByte(icon.z);
				StringSerializer.writeString(serializer, connection.getVersion(), "");
				VarNumberSerializer.writeVarInt(serializer, (icon.dirtype & 0x0F)); //TODO: Remap icon colors.
			}
		}

		if ((flags & FLAG_TEXTURE_UPDATE) != 0) {
			VarNumberSerializer.writeSVarInt(serializer, columns);
			VarNumberSerializer.writeSVarInt(serializer, rows);
			VarNumberSerializer.writeSVarInt(serializer, xstart);
			VarNumberSerializer.writeSVarInt(serializer, zstart);
			VarNumberSerializer.writeVarInt(serializer, colors.length);
			RemappingTable.ArrayBasedIdRemappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(connection.getVersion());
			for (int i = 0; i < colors.length; i++) {
					VarNumberSerializer.writeVarInt(serializer, colorRemapper.getRemap(colors[i] & 0xFF));
			}
		}

		return RecyclableSingletonList.create(serializer);
	}
}
