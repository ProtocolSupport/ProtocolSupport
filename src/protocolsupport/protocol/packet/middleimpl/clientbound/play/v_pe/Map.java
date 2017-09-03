package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorHelper;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap {
	
	public static final int FLAG_ENTITY_UPDATE = 0x08;
	public static final int FLAG_DECORATION_UPDATE = 0x04;
	public static final int FLAG_TEXTURE_UPDATE = 0x02;
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA, connection.getVersion());
		VarNumberSerializer.writeSVarLong(serializer, itemData);

		int flags = 0;

		if (columns > 0) { flags |= FLAG_TEXTURE_UPDATE; }
		if (icons.length > 0) { flags |= (FLAG_DECORATION_UPDATE) ; } //TODO: Fix the map icons.

		VarNumberSerializer.writeVarInt(serializer, flags);
		serializer.writeByte(0); //Dimension

		//Implementation
		if ((flags & (FLAG_DECORATION_UPDATE | FLAG_TEXTURE_UPDATE)) != 0) {
			serializer.writeByte(scale);
		}

		if ((flags & FLAG_DECORATION_UPDATE) != 0) {
			VarNumberSerializer.writeVarInt(serializer, 0); //Playerheads?
			VarNumberSerializer.writeVarInt(serializer, icons.length);
			for (Icon icon: icons) {
				serializer.writeByte(icon.dirtype & 0x0F);
				serializer.writeByte(icon.dirtype & 0xF0);
				serializer.writeByte(icon.x);
				serializer.writeByte(icon.z);
				StringSerializer.writeString(serializer, connection.getVersion(), "");
				//TODO: Remap icon colors. (Also: writeIntLE instead?)
				VarNumberSerializer.writeVarInt(serializer, MapColorHelper.toARGB((byte) 255, (byte) 255, (byte) 255, (byte) 255));
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
