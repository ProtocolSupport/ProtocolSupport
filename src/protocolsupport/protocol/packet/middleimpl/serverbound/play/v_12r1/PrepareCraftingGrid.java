package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class PrepareCraftingGrid extends ServerBoundMiddlePacket {

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readByte();
		clientdata.readShort();
		Function<ByteBuf, Void> elementReader = from -> {
			ItemStackSerializer.readItemStack(from, connection.getVersion(), cache.getAttributesCache().getLocale(), true);
			from.readByte();
			from.readByte();
			return null;
		};
		ArraySerializer.readShortTArray(clientdata, Void.class, elementReader);
		ArraySerializer.readShortTArray(clientdata, Void.class, elementReader);
	}

}
