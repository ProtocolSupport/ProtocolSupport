package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class BlockTileUpdate extends ServerBoundMiddlePacket {

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected NBTTagCompoundWrapper nbt;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readPEPosition(clientdata);
		nbt = ItemStackSerializer.readTag(clientdata, true, ProtocolVersion.MINECRAFT_PE);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (nbt.hasKeyOfType("id", NBTTagType.STRING)) {
			switch (nbt.getString("id")) {
				case "Sign": {
					cache.getPETileCache().updateSignTag(nbt);
					break;
				}
				case "Beacon": {
					if (nbt.hasKeyOfType("primary", NBTTagType.INT) && nbt.hasKeyOfType("secondary", NBTTagType.INT)) {
						return RecyclableSingletonList.create(createSelectBeacon(nbt.getIntNumber("primary"), nbt.getIntNumber("secondary")));
					}
				}
			}
		}
		return RecyclableEmptyList.get();
	}

	private static ServerBoundPacketData createSelectBeacon(int primary, int secondary) {
		ByteBuf payload = Unpooled.buffer();
		payload.writeInt(primary);
		payload.writeInt(secondary);
		return MiddleCustomPayload.create("MC|Beacon", MiscSerializer.readAllBytes(payload));
	}

}
