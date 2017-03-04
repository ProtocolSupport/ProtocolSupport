package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
		StringSerializer.writeString(creator, ProtocolVersion.getLatest(), locale);
		creator.writeByte(viewDist);
		VarNumberSerializer.writeVarInt(creator, chatMode);
		creator.writeBoolean(chatColors);
		creator.writeByte(skinFlags);
		VarNumberSerializer.writeVarInt(creator, mainHand);
		return RecyclableSingletonList.create(creator);
	}

}
