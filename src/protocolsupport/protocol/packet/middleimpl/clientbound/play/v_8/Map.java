package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Map extends MiddleMap {

	public Map(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_MAP_ID);
		VarNumberSerializer.writeVarInt(serializer, itemData);
		serializer.writeByte(scale);
		ArraySerializer.writeVarIntTArray(serializer, icons, (to, icon) -> {
			to.writeByte(((icon.type <= 9 ? icon.type : 0) << 4) | icon.direction);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
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
