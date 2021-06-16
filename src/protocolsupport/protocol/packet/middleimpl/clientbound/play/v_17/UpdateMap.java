package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap {

	public UpdateMap(MiddlePacketInit init) {
		super(init);
	}

	@Override
	public void write() {
		ClientBoundPacketData updatemapPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
		VarNumberSerializer.writeVarInt(updatemapPacket, id);
		updatemapPacket.writeByte(scale);
		updatemapPacket.writeBoolean(locked);
		updatemapPacket.writeBoolean(showIcons);
		if (showIcons) {
			ArraySerializer.writeVarIntTArray(updatemapPacket, icons, (to, icon) -> {
				VarNumberSerializer.writeVarInt(to, icon.type);
				to.writeByte(icon.x);
				to.writeByte(icon.z);
				to.writeByte(icon.direction);
				to.writeBoolean(icon.displayName != null);
				if (icon.displayName != null) {
					StringSerializer.writeVarIntUTF8String(to, icon.displayName);
				}
			});
		}
		updatemapPacket.writeByte(columns);
		if (columns > 0) {
			ArrayBasedIntMappingTable colorRemapper = MapColorMappingRegistry.INSTANCE.getTable(version);
			for (int i = 0; i < colors.length; i++) {
				colors[i] = (byte) colorRemapper.get(colors[i] & 0xFF);
			}
			updatemapPacket.writeByte(rows);
			updatemapPacket.writeByte(xstart);
			updatemapPacket.writeByte(zstart);
			ArraySerializer.writeVarIntByteArray(updatemapPacket, colors);
		}
		codec.writeClientbound(updatemapPacket);
	}

}
