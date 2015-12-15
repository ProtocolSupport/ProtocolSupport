package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected BlockPosition position;
	protected String[] lines = new String[4];

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_UPDATE_SIGN.get());
		creator.writePosition(position);
		for (int i = 0; i < lines.length; i++) {
			creator.writeString(ChatSerializer.a(new ChatComponentText(lines[i])));
		}
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
