package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public UpdateMap(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData updatemap = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
		VarNumberCodec.writeVarInt(updatemap, id);
		updatemap.writeByte(scale);
		updatemap.writeBoolean(showIcons);
		ArrayCodec.writeVarIntTArray(updatemap, icons, (to, icon) -> {
			to.writeByte(((icon.type <= 9 ? icon.type : 0) << 4) | icon.direction);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
		});
		updatemap.writeByte(columns);
		if (columns > 0) {
			ArrayBasedIntMappingTable colorRemapper = MapColorMappingRegistry.INSTANCE.getTable(version);
			for (int i = 0; i < colors.length; i++) {
				colors[i] = (byte) colorRemapper.get(colors[i] & 0xFF);
			}
			updatemap.writeByte(rows);
			updatemap.writeByte(xstart);
			updatemap.writeByte(zstart);
			ArrayCodec.writeVarIntByteArray(updatemap, colors);
		}
		io.writeClientbound(updatemap);
	}

}
