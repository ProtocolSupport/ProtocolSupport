package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected String string;
	protected boolean assumecommand;
	protected Position position;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_TAB_COMPLETE);
		creator.writeString(string);
		creator.writeBoolean(assumecommand);
		if (position != null) {
			creator.writeBoolean(true);
			creator.writePosition(position);
		} else {
			creator.writeBoolean(false);
		}
		return RecyclableSingletonList.create(creator);
	}

}
