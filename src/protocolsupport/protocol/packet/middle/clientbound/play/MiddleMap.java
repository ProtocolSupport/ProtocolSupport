package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleMap extends ClientBoundMiddlePacket {

	protected int itemData;
	protected int scale;
	protected boolean showIcons;
	protected Icon[] icons;
	protected int columns;
	protected int rows;
	protected int xstart;
	protected int zstart;
	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		itemData = VarNumberSerializer.readVarInt(serverdata);
		scale = serverdata.readUnsignedByte();
		showIcons = serverdata.readBoolean();
		icons = new Icon[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < icons.length; i++) {
			Icon icon = new Icon();
			icon.dirtype = serverdata.readUnsignedByte();
			icon.x = serverdata.readUnsignedByte();
			icon.z = serverdata.readUnsignedByte();
			icons[i] = icon;
		}
		columns = serverdata.readUnsignedByte();
		if (columns > 0) {
			rows = serverdata.readUnsignedByte();
			xstart = serverdata.readUnsignedByte();
			zstart = serverdata.readUnsignedByte();
			data = ByteArraySerializer.readByteArray(serverdata, ProtocolVersion.getLatest());
		}
	}

	protected static class Icon {
		public int dirtype;
		public int x;
		public int z;
	}

}
