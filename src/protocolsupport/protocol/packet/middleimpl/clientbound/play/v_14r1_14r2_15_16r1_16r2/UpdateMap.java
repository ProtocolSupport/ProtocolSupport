package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap {

	public UpdateMap(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData updatemap = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP);
		VarNumberSerializer.writeVarInt(updatemap, id);
		updatemap.writeByte(scale);
		updatemap.writeBoolean(showIcons);
		updatemap.writeBoolean(locked);
		ArraySerializer.writeVarIntTArray(updatemap, icons, (to, icon) -> {
			VarNumberSerializer.writeVarInt(to, icon.type);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
			to.writeByte(icon.direction);
			to.writeBoolean(icon.displayName != null);
			if (icon.displayName != null) {
				StringSerializer.writeVarIntUTF8String(to, icon.displayName);
			}
		});
		updatemap.writeByte(columns);
		if (columns > 0) {
			ArrayBasedIntMappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(version);
			for (int i = 0; i < colors.length; i++) {
				colors[i] = (byte) colorRemapper.get(colors[i] & 0xFF);
			}
			updatemap.writeByte(rows);
			updatemap.writeByte(xstart);
			updatemap.writeByte(zstart);
			ArraySerializer.writeVarIntByteArray(updatemap, colors);
		}
		codec.write(updatemap);
	}

}
