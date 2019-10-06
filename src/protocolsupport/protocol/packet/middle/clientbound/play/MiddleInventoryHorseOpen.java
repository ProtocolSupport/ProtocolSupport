package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.window.AbstractWindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleInventoryHorseOpen extends ClientBoundMiddlePacket {

	protected final AbstractWindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);

	public MiddleInventoryHorseOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected byte windowId;
	protected int slots;
	protected int entityId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		slots = VarNumberSerializer.readVarInt(serverdata);
		entityId = serverdata.readInt();
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWindowCache().setOpenedWindow(windowId, WindowType.HORSE, windowsRemapper.get(WindowType.HORSE, slots));
		return true;
	}

}
