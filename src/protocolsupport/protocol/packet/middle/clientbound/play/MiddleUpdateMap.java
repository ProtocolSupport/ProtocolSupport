package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleUpdateMap extends ClientBoundMiddlePacket {

	public MiddleUpdateMap(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected int scale;
	protected boolean showIcons;
	protected boolean locked;
	protected Icon[] icons;
	protected int columns;
	protected int rows;
	protected int xstart;
	protected int zstart;
	protected byte[] colors;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		scale = serverdata.readUnsignedByte();
		showIcons = serverdata.readBoolean();
		locked = serverdata.readBoolean();
		icons = ArraySerializer.readVarIntTArray(
			serverdata, Icon.class,
			from -> new Icon(
				VarNumberSerializer.readVarInt(from),
				from.readUnsignedByte(),
				from.readUnsignedByte(),
				from.readByte(),
				from.readBoolean() ? StringSerializer.readVarIntUTF8String(serverdata) : null
			)
		);
		columns = serverdata.readUnsignedByte();
		if (columns > 0) {
			rows = serverdata.readUnsignedByte();
			xstart = serverdata.readUnsignedByte();
			zstart = serverdata.readUnsignedByte();
			colors = ArraySerializer.readVarIntByteArray(serverdata);
		}
	}

	public static class Icon {
		//TODO: enum for icon types
		public final int type;
		public final int x;
		public final int z;
		public final int direction;
		public final String displayName;
		public Icon(int type, int x, int z, int direction, String displayName) {
			this.type = type;
			this.x = x;
			this.z = z;
			this.direction = direction;
			this.displayName = displayName;
		}
	}

}
