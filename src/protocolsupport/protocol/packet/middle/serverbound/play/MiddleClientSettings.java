package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.inventory.MainHand;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected MiddleClientSettings(MiddlePacketInit init) {
		super(init);
	}

	protected String locale;
	protected int viewDist;
	protected ChatMode chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected MainHand mainHand;
	protected boolean disableTextFilter;

	@Override
	protected void write() {
		cache.getClientCache().setLocale(locale);

		codec.writeServerbound(create(locale, viewDist, chatMode, chatColors, skinFlags, mainHand, disableTextFilter));
	}

	public static ServerBoundPacketData create(String locale, int viewDist, ChatMode chatMode, boolean chatColors, int skinFlags, MainHand mainHand, boolean disableTextFilter) {
		ServerBoundPacketData clientsettingsPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SETTINGS);
		StringSerializer.writeVarIntUTF8String(clientsettingsPacket, locale);
		clientsettingsPacket.writeByte(viewDist);
		MiscSerializer.writeVarIntEnum(clientsettingsPacket, chatMode);
		clientsettingsPacket.writeBoolean(chatColors);
		clientsettingsPacket.writeByte(skinFlags);
		MiscSerializer.writeVarIntEnum(clientsettingsPacket, mainHand);
		clientsettingsPacket.writeBoolean(disableTextFilter);
		return clientsettingsPacket;
	}

	protected enum ChatMode {
		NORMAL, ONLY_SYSTEM_MESSAGES, HIDDEN;
		public static final EnumConstantLookup<ChatMode> CONSTANT_LOOKUP = new EnumConstantLookup<>(ChatMode.class);
	}

}
