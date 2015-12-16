package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleMap;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.protocol.transformer.utils.MapTransformer;
import protocolsupport.protocol.transformer.utils.MapTransformer.ColumnEntry;

@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5})
public class Map extends MiddleMap<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		Collection<PacketData> datas = new ArrayList<PacketData>();
		PacketDataSerializer scaledata = PacketDataSerializer.createNew(version);
		scaledata.writeVarInt(itemData);
		scaledata.writeShort(2);
		scaledata.writeByte(2);
		scaledata.writeByte(scale);
		datas.add(new PacketData(ClientBoundPacket.PLAY_MAP_ID, scaledata));
		if (icons.length > 0) {
			PacketDataSerializer iconsdata = PacketDataSerializer.createNew(version);
			iconsdata.writeVarInt(itemData);
			iconsdata.writeShort(icons.length * 3 + 1);
			iconsdata.writeByte(1);
			for (Icon icon : icons) {
				iconsdata.writeByte(icon.dirtype);
				iconsdata.writeByte(icon.x);
				iconsdata.writeByte(icon.z);
			}
			datas.add(new PacketData(ClientBoundPacket.PLAY_MAP_ID, iconsdata));
		}
		if (columns > 0) {
			MapTransformer maptransformer = new MapTransformer();
			maptransformer.loadFromNewMapData(columns, rows, xstart, zstart, data);
			for (ColumnEntry entry : maptransformer.toPre18MapData()) {
				PacketDataSerializer mapdata = PacketDataSerializer.createNew(version);
				mapdata.writeVarInt(itemData);
				mapdata.writeShort(3 + entry.getColors().length);
				mapdata.writeByte(0);
				mapdata.writeByte(entry.getX());
				mapdata.writeByte(entry.getY());
				mapdata.writeBytes(entry.getColors());
				datas.add(new PacketData(ClientBoundPacket.PLAY_MAP_ID, mapdata));
			}
		}
		return datas;
	}

}
