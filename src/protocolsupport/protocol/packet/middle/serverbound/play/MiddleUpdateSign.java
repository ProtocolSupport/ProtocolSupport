package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected Position position;
	protected String[] lines = new String[4];

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_UPDATE_SIGN);
		creator.writePosition(position);
		for (int i = 0; i < lines.length; i++) {
			creator.writeString(lines[i]);
		}
		return RecyclableSingletonList.create(creator);
	}

}
