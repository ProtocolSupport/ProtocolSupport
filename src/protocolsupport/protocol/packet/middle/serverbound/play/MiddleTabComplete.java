package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
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
