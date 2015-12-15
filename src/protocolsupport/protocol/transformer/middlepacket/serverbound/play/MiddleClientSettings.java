package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected String locale;
	protected int viewDist;
	protected byte chatMode;
	protected boolean chatColors;
	protected byte skinFlags;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_SETTINGS.get());
		creator.writeString(locale);
		creator.writeByte(viewDist);
		creator.writeByte(chatMode);
		creator.writeBoolean(chatColors);
		creator.writeByte(skinFlags);
		return Collections.<Packet<?>> singletonList(creator.create());
	}

}
