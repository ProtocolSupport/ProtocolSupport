package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ReleaseItem extends ServerBoundMiddlePacket {

	public ReleaseItem(ConnectionImpl connection) {
		super(connection);
	}

	protected int subTypeId = -1;
	protected NetworkItemStack itemstack;
	protected int slot;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		subTypeId = VarNumberSerializer.readVarInt(clientdata);
		slot = VarNumberSerializer.readSVarInt(clientdata);
		itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale());
		clientdata.readFloatLE();
		clientdata.readFloatLE();
		clientdata.readFloatLE();
	}

	protected static final int ACTION_RELEASE = 0;
	protected static final int ACTION_CONSUME = 1;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (subTypeId == ACTION_RELEASE) {
			return RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_USE, new Position(0, -0, 0), 0));
		}
		return RecyclableEmptyList.get();
	}

}
