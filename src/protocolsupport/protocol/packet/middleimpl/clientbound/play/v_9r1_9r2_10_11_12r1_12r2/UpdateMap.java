package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
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
