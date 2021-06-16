package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap.ColumnEntry;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap {

	public UpdateMap(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scaledata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
		VarNumberSerializer.writeVarInt(scaledata, id);
		scaledata.writeShort(2);
		scaledata.writeByte(2);
		scaledata.writeByte(scale);
		codec.writeClientbound(scaledata);

		if (icons.length > 0) {
			ClientBoundPacketData iconsdata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
			VarNumberSerializer.writeVarInt(iconsdata, id);
			iconsdata.writeShort((icons.length * 3) + 1);
			iconsdata.writeByte(1);
			for (Icon icon : icons) {
				iconsdata.writeByte(((icon.type <= 9 ? icon.type : 0) << 4) | icon.direction);
				iconsdata.writeByte(icon.x);
				iconsdata.writeByte(icon.z);
			}
			codec.writeClientbound(iconsdata);
		}

		if (columns > 0) {
			LegacyMap maptransformer = new LegacyMap();
			maptransformer.loadFromNewMapData(columns, rows, xstart, zstart, colors);
			ArrayBasedIntMappingTable colorRemapper = MapColorMappingRegistry.INSTANCE.getTable(version);
			for (ColumnEntry entry : maptransformer.toPre18MapData()) {
				ClientBoundPacketData mapdata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
				VarNumberSerializer.writeVarInt(mapdata, id);
				mapdata.writeShort(3 + entry.getColors().length);
				mapdata.writeByte(0);
				mapdata.writeByte(entry.getX());
				mapdata.writeByte(entry.getY());
				byte[] colors = entry.getColors();
				for (int i = 0; i < colors.length; i++) {
					colors[i] = (byte) colorRemapper.get(colors[i] & 0xFF);
				}
				mapdata.writeBytes(colors);
				codec.writeClientbound(mapdata);
			}
		}
	}

}
