//TODO: fix Map format
//package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;
//
//import protocolsupport.api.ProtocolVersion;
//import protocolsupport.protocol.ConnectionImpl;
//import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
//import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
//import protocolsupport.protocol.serializer.StringSerializer;
//import protocolsupport.protocol.serializer.VarNumberSerializer;
//import protocolsupport.protocol.typeremapper.mapcolor.MapColorHelper;
//import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
//import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
//import protocolsupport.protocol.typeremapper.utils.RemappingTable;
//import protocolsupport.utils.recyclable.RecyclableCollection;
//import protocolsupport.utils.recyclable.RecyclableSingletonList;
//
//public class Map extends MiddleMap {
//
//	public Map(ConnectionImpl connection) {
//		super(connection);
//	}
//
//	//public static final int FLAG_ENTITY_UPDATE = 0x08;
//	protected static final int FLAG_DECORATION_UPDATE = 0x04;
//	protected static final int FLAG_TEXTURE_UPDATE = 0x02;
//
//	@Override
//	public RecyclableCollection<ClientBoundPacketData> toData() {
//		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA);
//		VarNumberSerializer.writeSVarLong(serializer, itemData);
//
//		int flags = 0;
//
//		if (columns > 0) { flags |= FLAG_TEXTURE_UPDATE; }
//		//if (icons.length > 0) { flags |= (FLAG_DECORATION_UPDATE) ; } //TODO: Fix the map icons.
//
//		VarNumberSerializer.writeVarInt(serializer, flags);
//		serializer.writeByte(ChangeDimension.getPeDimensionId(cache.getAttributesCache().getDimension())); //Dimension
//
//		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_11)) {
//			serializer.writeBoolean(false); //is locked
//		}
//
//		//Implementation
//		if ((flags & (FLAG_DECORATION_UPDATE | FLAG_TEXTURE_UPDATE)) != 0) {
//			serializer.writeByte(scale);
//		}
//
//		//TODO check if this is right.
//		if ((flags & FLAG_DECORATION_UPDATE) != 0) {
//			VarNumberSerializer.writeVarInt(serializer, 0); //Playerheads?
//			VarNumberSerializer.writeVarInt(serializer, icons.length);
//			for (Icon icon : icons) {
//				serializer.writeByte(icon.direction);
//				serializer.writeByte(icon.type);
//				serializer.writeByte(icon.x);
//				serializer.writeByte(icon.z);
//				StringSerializer.writeString(serializer, connection.getVersion(), ""); //Label
//				VarNumberSerializer.writeVarInt(serializer, MapColorHelper.toARGB((byte) 255, (byte) 255, (byte) 255, (byte) 255)); //TODO: Remap icon colors.
//			}
//		}
//
//		if ((flags & FLAG_TEXTURE_UPDATE) != 0) {
//			VarNumberSerializer.writeSVarInt(serializer, columns);
//			VarNumberSerializer.writeSVarInt(serializer, rows);
//			VarNumberSerializer.writeSVarInt(serializer, xstart);
//			VarNumberSerializer.writeSVarInt(serializer, zstart);
//			VarNumberSerializer.writeVarInt(serializer, columns * rows);
//			RemappingTable.ArrayBasedIdRemappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(connection.getVersion());
//			for (int i = 0; i < colors.length; i++) {
//				VarNumberSerializer.writeVarInt(serializer, colorRemapper.getRemap(colors[i] & 0xFF));
//			}
//		}
//
//		return RecyclableSingletonList.create(serializer);
//	}
//
//}
