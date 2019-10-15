package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSelectTrade extends ServerBoundMiddlePacket {

	public MiddleSelectTrade(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SELECT_TRADE);
		VarNumberSerializer.writeVarInt(creator, slot);
		return creator;
	}

}
