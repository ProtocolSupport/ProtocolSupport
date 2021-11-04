package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUpdateMap;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class UpdateMap extends MiddleUpdateMap implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2 {

	public UpdateMap(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData updatemap = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_MAP);
		VarNumberCodec.writeVarInt(updatemap, id);
		updatemap.writeByte(scale);
		updatemap.writeBoolean(showIcons);
		updatemap.writeBoolean(locked);
		ArrayCodec.writeVarIntTArray(updatemap, icons, (to, icon) -> {
			VarNumberCodec.writeVarInt(to, icon.type);
			to.writeByte(icon.x);
			to.writeByte(icon.z);
			to.writeByte(icon.direction);
			to.writeBoolean(icon.displayName != null);
			if (icon.displayName != null) {
				StringCodec.writeVarIntUTF8String(to, icon.displayName);
			}
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
