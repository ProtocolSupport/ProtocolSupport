package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	protected int type;
	protected NBTTagCompoundWrapper tag;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		type = serverdata.readUnsignedByte();
		tag = ItemStackSerializer.readTag(serverdata, ProtocolVersion.getLatest());
	}

}
