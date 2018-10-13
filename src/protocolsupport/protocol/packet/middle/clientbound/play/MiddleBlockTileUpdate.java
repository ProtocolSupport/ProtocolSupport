package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	public MiddleBlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected int type;
	protected NBTCompound tag;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		type = serverdata.readUnsignedByte();
		tag = ItemStackSerializer.readTag(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
