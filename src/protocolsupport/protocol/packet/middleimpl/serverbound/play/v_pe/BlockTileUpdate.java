package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends ServerBoundMiddlePacket {

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected NBTCompound nbt;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readPEPosition(clientdata);
		nbt = ItemStackSerializer.readTag(clientdata, true, connection.getVersion());
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		NBTString id = nbt.getTagOfType("id", NBTType.STRING);
		if (id != null) {
			switch (id.getValue()) {
				case "Sign": {
					cache.getPETileCache().updateSignTag(nbt);
					break;
				}
				case "Beacon": {
					NBTNumber primary = nbt.getNumberTag("primary");
					NBTNumber secondary = nbt.getNumberTag("secondary");
					if (primary != null && secondary != null) {
						return RecyclableSingletonList.create(createSelectBeacon(primary.getAsInt(), secondary.getAsInt()));
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
		return MiddleCustomPayload.create("Minecraft:Beacon", MiscSerializer.readAllBytes(payload));
	}

}
