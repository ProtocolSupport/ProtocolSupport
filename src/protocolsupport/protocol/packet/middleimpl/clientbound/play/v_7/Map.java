package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyMap;
import protocolsupport.protocol.legacyremapper.LegacyMap.ColumnEntry;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Map extends MiddleMap {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableCollection<ClientBoundPacketData> datas = RecyclableArrayList.create();
		ClientBoundPacketData scaledata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_MAP_ID, version);
		VarNumberSerializer.writeVarInt(scaledata, itemData);
		scaledata.writeShort(2);
		scaledata.writeByte(2);
		scaledata.writeByte(scale);
		datas.add(scaledata);
		if (icons.length > 0) {
			ClientBoundPacketData iconsdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_MAP_ID, version);
			VarNumberSerializer.writeVarInt(iconsdata, itemData);
			iconsdata.writeShort((icons.length * 3) + 1);
			iconsdata.writeByte(1);
			for (Icon icon : icons) {
				iconsdata.writeByte(icon.dirtype);
				iconsdata.writeByte(icon.x);
				iconsdata.writeByte(icon.z);
			}
			datas.add(iconsdata);
		}
		if (columns > 0) {
			LegacyMap maptransformer = new LegacyMap();
			maptransformer.loadFromNewMapData(columns, rows, xstart, zstart, data);
			for (ColumnEntry entry : maptransformer.toPre18MapData()) {
				ClientBoundPacketData mapdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_MAP_ID, version);
				VarNumberSerializer.writeVarInt(mapdata, itemData);
				mapdata.writeShort(3 + entry.getColors().length);
				mapdata.writeByte(0);
				mapdata.writeByte(entry.getX());
				mapdata.writeByte(entry.getY());
				mapdata.writeBytes(entry.getColors());
				datas.add(mapdata);
			}
		}
		return datas;
	}

}
