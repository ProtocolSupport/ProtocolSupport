package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleMap extends ClientBoundMiddlePacket {

	protected int itemData;
	protected int scale;
	protected boolean showIcons;
	protected Icon[] icons;
	protected int columns;
	protected int rows;
	protected int xstart;
	protected int zstart;
	protected byte[] colors;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		itemData = VarNumberSerializer.readVarInt(serverdata);
		scale = serverdata.readUnsignedByte();
		showIcons = serverdata.readBoolean();
		icons = ArraySerializer.readVarIntTArray(
			serverdata, Icon.class,
			(from) -> new Icon(from.readUnsignedByte(), from.readUnsignedByte(), from.readUnsignedByte())
		);
		columns = serverdata.readUnsignedByte();
		if (columns > 0) {
			rows = serverdata.readUnsignedByte();
			xstart = serverdata.readUnsignedByte();
			zstart = serverdata.readUnsignedByte();
			colors = ArraySerializer.readByteArray(serverdata, ProtocolVersionsHelper.LATEST_PC);
		}
	}

	public static class Icon {
		public int dirtype;
		public int x;
		public int z;
		public Icon(int dirtype, int x, int z) {
			this.dirtype = dirtype;
			this.x = x;
			this.z = z;
		}
	}

}
