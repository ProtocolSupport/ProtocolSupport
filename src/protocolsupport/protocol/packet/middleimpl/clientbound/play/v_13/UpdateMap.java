package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UpdateMap extends MiddleUpdateMap {

	public UpdateMap(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP);
		VarNumberSerializer.writeVarInt(serializer, id);
		serializer.writeByte(scale);
		serializer.writeBoolean(showIcons);
		ArraySerializer.writeVarIntTArray(serializer, icons, (to, icon) -> {
			VarNumberSerializer.writeVarInt(to, icon.type);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
			to.writeByte(icon.direction);
			to.writeBoolean(icon.displayName != null);
			if (icon.displayName != null) {
				StringSerializer.writeVarIntUTF8String(to, icon.displayName);
			}
		});
		serializer.writeByte(columns);
		if (columns > 0) {
			ArrayBasedIdRemappingTable colorRemapper = MapColorRemapper.REMAPPER.getTable(version);
			for (int i = 0; i < colors.length; i++) {
				colors[i] = (byte) colorRemapper.getRemap(colors[i] & 0xFF);
			}
			serializer.writeByte(rows);
			serializer.writeByte(xstart);
			serializer.writeByte(zstart);
			ArraySerializer.writeVarIntByteArray(serializer, colors);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
