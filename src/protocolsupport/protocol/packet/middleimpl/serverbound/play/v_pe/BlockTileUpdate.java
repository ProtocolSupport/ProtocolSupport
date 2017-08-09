package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends ServerBoundMiddlePacket {

	protected NBTTagCompoundWrapper nbt;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readPEPosition(clientdata);
		nbt = ItemStackSerializer.readTag(clientdata, true, ProtocolVersion.MINECRAFT_PE);
		if (nbt.getString("id").equals("Sign")) {
			this.cache.setSignTag(nbt);
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}
}
