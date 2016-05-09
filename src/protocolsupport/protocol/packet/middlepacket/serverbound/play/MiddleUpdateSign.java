package protocolsupport.protocol.packet.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected BlockPosition position;
	protected String[] lines = new String[4];

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_UPDATE_SIGN.get());
		creator.writePosition(position);
		for (int i = 0; i < lines.length; i++) {
			creator.writeString(lines[i]);
		}
		return RecyclableSingletonList.create(creator.create());
	}

}
