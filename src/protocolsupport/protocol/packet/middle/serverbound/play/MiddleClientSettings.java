package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected String locale;
	protected int viewDist;
	protected int chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected int mainHand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SETTINGS);
		creator.writeString(locale);
		creator.writeByte(viewDist);
		creator.writeVarInt(chatMode);
		creator.writeBoolean(chatColors);
		creator.writeByte(skinFlags);
		creator.writeVarInt(mainHand);
		return RecyclableSingletonList.create(creator);
	}

}
