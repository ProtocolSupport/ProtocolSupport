package protocolsupport.protocol.packet.middle.base.serverbound.play;

import org.bukkit.inventory.MainHand;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected MiddleClientSettings(IMiddlePacketInit init) {
		super(init);
	}

	protected String locale;
	protected int viewDist;
	protected ChatMode chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected MainHand mainHand;
	protected boolean disableTextFilter;
	protected boolean list;

	@Override
	protected void write() {
		cache.getClientCache().setLocale(locale);

		io.writeServerbound(create(locale, viewDist, chatMode, chatColors, skinFlags, mainHand, disableTextFilter, list));
	}

	public static ServerBoundPacketData create(String locale, int viewDist, ChatMode chatMode, boolean chatColors, int skinFlags, MainHand mainHand, boolean disableTextFilter, boolean list) {
		ServerBoundPacketData clientsettingsPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SETTINGS);
		StringCodec.writeVarIntUTF8String(clientsettingsPacket, locale);
		clientsettingsPacket.writeByte(viewDist);
		MiscDataCodec.writeVarIntEnum(clientsettingsPacket, chatMode);
		clientsettingsPacket.writeBoolean(chatColors);
		clientsettingsPacket.writeByte(skinFlags);
		MiscDataCodec.writeVarIntEnum(clientsettingsPacket, mainHand);
		clientsettingsPacket.writeBoolean(disableTextFilter);
		clientsettingsPacket.writeBoolean(list);
		return clientsettingsPacket;
	}

	protected enum ChatMode {
		NORMAL, ONLY_SYSTEM_MESSAGES, HIDDEN;
		public static final EnumConstantLookup<ChatMode> CONSTANT_LOOKUP = new EnumConstantLookup<>(ChatMode.class);
	}

}
