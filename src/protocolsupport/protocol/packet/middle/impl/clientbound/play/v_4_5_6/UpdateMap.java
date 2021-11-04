package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import org.bukkit.Material;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap;
import protocolsupport.protocol.typeremapper.legacy.LegacyMap.ColumnEntry;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public UpdateMap(IMiddlePacketInit init) {
		super(init);
	}

	@SuppressWarnings("deprecation")
	private static final int mapId = Material.LEGACY_MAP.getId();

	@Override
	protected void write() {
		ClientBoundPacketData scaledata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
		scaledata.writeShort(mapId);
		scaledata.writeShort(id);
		scaledata.writeShort(2);
		scaledata.writeByte(2);
		scaledata.writeByte(scale);
		io.writeClientbound(scaledata);

		if (icons.length > 0) {
			ClientBoundPacketData iconsdata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
			iconsdata.writeShort(mapId);
			iconsdata.writeShort(id);
			iconsdata.writeShort((icons.length * 3) + 1);
			iconsdata.writeByte(1);
			for (Icon icon : icons) {
				iconsdata.writeByte(((icon.type <= 9 ? icon.type : 0) << 4) | icon.direction);
				iconsdata.writeByte(icon.x);
				iconsdata.writeByte(icon.z);
			}
			io.writeClientbound(iconsdata);
		}

		if (columns > 0) {
			LegacyMap maptransformer = new LegacyMap();
			maptransformer.loadFromNewMapData(columns, rows, xstart, zstart, colors);
			ArrayBasedIntMappingTable colorRemapper = MapColorMappingRegistry.INSTANCE.getTable(version);
			for (ColumnEntry entry : maptransformer.toPre18MapData()) {
				ClientBoundPacketData mapdata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
				mapdata.writeShort(mapId);
				mapdata.writeShort(id);
				mapdata.writeShort(3 + entry.getColors().length);
				mapdata.writeByte(0);
				mapdata.writeByte(entry.getX());
				mapdata.writeByte(entry.getY());
				byte[] colors = entry.getColors();
				for (int i = 0; i < colors.length; i++) {
					colors[i] = (byte) colorRemapper.get(colors[i] & 0xFF);
				}
				mapdata.writeBytes(colors);
				io.writeClientbound(mapdata);
			}
		}
	}

}
