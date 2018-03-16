package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class ReleaseItem extends ServerBoundMiddlePacket {

	protected int subTypeId = -1;
	protected ItemStackWrapper itemstack;
	protected int slot;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		subTypeId = VarNumberSerializer.readVarInt(clientdata);
		slot = VarNumberSerializer.readSVarInt(clientdata);
		itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale(), true);
		clientdata.readFloatLE(); clientdata.readFloatLE(); clientdata.readFloatLE();
	}

	public static final int RELEASE_RELEASE = 0;
	public static final int RELEASE_CONSUME = 1;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (subTypeId == RELEASE_RELEASE) {
			RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_USE, new Position(0, -0, 0), 0));
		}
		return RecyclableEmptyList.get();
	}

}
