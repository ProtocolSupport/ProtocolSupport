package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleClientCommand extends ServerBoundMiddlePacket {

	protected int command;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
		creator.writeVarInt(command);
		return RecyclableSingletonList.create(creator);
	}

}
