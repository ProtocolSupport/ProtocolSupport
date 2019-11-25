package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap.ColumnEntry;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class UpdateMap extends MiddleUpdateMap {

	public UpdateMap(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData scaledata = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP);
		VarNumberSerializer.writeVarInt(scaledata, id);
		scaledata.writeShort(2);
		scaledata.writeByte(2);
		scaledata.writeByte(scale);
		codec.write(scaledata);

		if (icons.length > 0) {
			ClientBoundPacketData iconsdata = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP);
			VarNumberSerializer.writeVarInt(iconsdata, id);
			iconsdata.writeShort((icons.length * 3) + 1);
			iconsdata.writeByte(1);
			for (Icon icon : icons) {
				iconsdata.writeByte(((icon.type <= 9 ? icon.type : 0) << 4) | icon.direction);
				iconsdata.writeByte(icon.x);
				iconsdata.writeByte(icon.z);
			}
			codec.write(iconsdata);
		}

		if (columns > 0) {
			LegacyMap maptransformer = new LegacyMap();
			maptransformer.loadFromNewMapData(columns, rows, xstart, zstart, colors);
			ArrayBasedIdRemappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(version);
			for (ColumnEntry entry : maptransformer.toPre18MapData()) {
				ClientBoundPacketData mapdata = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP);
				VarNumberSerializer.writeVarInt(mapdata, id);
				mapdata.writeShort(3 + entry.getColors().length);
				mapdata.writeByte(0);
				mapdata.writeByte(entry.getX());
				mapdata.writeByte(entry.getY());
				byte[] colors = entry.getColors();
				for (int i = 0; i < colors.length; i++) {
					colors[i] = (byte) colorRemapper.getRemap(colors[i] & 0xFF);
				}
				mapdata.writeBytes(colors);
				codec.write(mapdata);
			}
		}
	}

}
