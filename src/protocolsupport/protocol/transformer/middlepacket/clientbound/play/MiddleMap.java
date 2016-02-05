package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleMap<T> extends ClientBoundMiddlePacket<T> {

	protected int itemData;
	protected int scale;
	protected Icon[] icons;
	protected int columns;
	protected int rows;
	protected int xstart;
	protected int zstart;
	protected byte[] data;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		itemData = serializer.readVarInt();
		scale = serializer.readUnsignedByte();
		icons = new Icon[serializer.readVarInt()];
		for (int i = 0; i < icons.length; i++) {
			Icon icon = new Icon();
			icon.dirtype = serializer.readUnsignedByte();
			icon.x = serializer.readUnsignedByte();
			icon.z = serializer.readUnsignedByte();
			icons[i] = icon;
		}
		columns = serializer.readUnsignedByte();
		if (columns > 0) {
			rows = serializer.readUnsignedByte();
			xstart = serializer.readUnsignedByte();
			zstart = serializer.readUnsignedByte();
			data = serializer.readArray();
		}
	}

	protected static class Icon {
		public int dirtype;
		public int x;
		public int z;
	}

}
