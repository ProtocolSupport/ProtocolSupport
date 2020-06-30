package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.inventory.MainHand;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	public MiddleClientSettings(ConnectionImpl connection) {
		super(connection);
	}

	protected String locale;
	protected int viewDist;
	protected ChatMode chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected MainHand mainHand;

	@Override
	protected void writeToServer() {
		cache.getClientCache().setLocale(locale);

		codec.read(create(locale, viewDist, chatMode, chatColors, skinFlags, mainHand));
	}

	public static ServerBoundPacketData create(String locale, int viewDist, ChatMode chatMode, boolean chatColors, int skinFlags, MainHand mainHand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SETTINGS);
		StringSerializer.writeVarIntUTF8String(creator, locale);
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
