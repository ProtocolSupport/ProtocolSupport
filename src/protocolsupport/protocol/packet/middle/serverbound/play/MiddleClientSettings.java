package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.inventory.MainHand;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected String locale;
	protected int viewDist;
	protected ChatMode chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected MainHand mainHand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		cache.setLocale(locale);
		return RecyclableSingletonList.create(create(locale, viewDist, chatMode, chatColors, skinFlags, mainHand));
	}

	public static ServerBoundPacketData create(String locale, int viewDist, ChatMode chatMode, boolean chatColors, int skinFlags, MainHand mainHand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SETTINGS);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, locale);
		creator.writeByte(viewDist);
		MiscSerializer.writeVarIntEnum(creator, chatMode);
		creator.writeBoolean(chatColors);
		creator.writeByte(skinFlags);
		MiscSerializer.writeVarIntEnum(creator, mainHand);
		return creator;
	}

	protected static enum ChatMode {
		NORMAL, ONLY_SYSTEM_MESSAGES, HIDDEN;
		public static final EnumConstantLookups.EnumConstantLookup<ChatMode> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(ChatMode.class);
	}

}
