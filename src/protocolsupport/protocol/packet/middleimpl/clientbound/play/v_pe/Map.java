package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMap;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.awt.*;

public class Map extends MiddleMap {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("Sending map data for " + this.itemData + "... scale: " + this.scale + ", col: " + this.columns + ", rows: " + this.rows + ", xstart: " + this.xstart + ", zstart: " + this.zstart);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MAP_ITEM_DATA, connection.getVersion());

		VarNumberSerializer.writeSVarLong(serializer, this.itemData);

		// 0x08 = entity update
		// 0x04 = decoration update
		// 0x02 = texture update
		VarNumberSerializer.writeVarInt(serializer, 0x00);

		/* VarNumberSerializer.writeVarInt(serializer, 0x02); // texture update
		serializer.writeByte(1); // scale
		VarNumberSerializer.writeSVarInt(serializer, 128); // columns
		VarNumberSerializer.writeSVarInt(serializer, 128); // row
		VarNumberSerializer.writeSVarInt(serializer, 0); // offset X
		VarNumberSerializer.writeSVarInt(serializer, 0); // offset Y

		// data
		for (int y = 0; y < 128; y++) {
			for (int x = 0; x < 128; x++) {
				Color color = new Color(0, 162, 232);
				byte red = (byte) color.getRed();
				byte green = (byte) color.getGreen();
				byte blue = (byte) color.getBlue();

				VarNumberSerializer.writeVarInt(serializer, toRGB(red, green, blue, (byte) 0xff));
			}
		} */
		return RecyclableSingletonList.create(serializer);
	}

	private static int toRGB(byte r, byte g, byte b, byte a) {
		long result = (int) r & 0xff;
		result |= ((int) g & 0xff) << 8;
		result |= ((int) b & 0xff) << 16;
		result |= ((int) a & 0xff) << 24;
		return (int) (result & 0xFFFFFFFFL);
	}
}
