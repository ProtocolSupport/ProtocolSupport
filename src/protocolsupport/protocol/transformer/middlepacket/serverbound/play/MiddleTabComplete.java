package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected String string;
	protected boolean assumecommand;
	protected BlockPosition position;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_TAB_COMPLETE.get());
		creator.writeString(string);
		creator.writeBoolean(assumecommand);
		if (position != null) {
			creator.writeBoolean(true);
			creator.writePosition(position);
		} else {
			creator.writeBoolean(false);
		}
		return RecyclableSingletonList.create(creator.create());
	}

}
