package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	protected int type;
	protected NBTTagCompoundWrapper tag;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		type = serverdata.readUnsignedByte();
		tag = ItemStackSerializer.readTag(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
