package protocolsupport.protocol;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;

public enum ServerBoundPacket {

	HANDSHAKE_START(0x00, EnumProtocol.HANDSHAKING),
	STATUS_PING(0x00, EnumProtocol.STATUS),
	LOGIN_START(0x00, EnumProtocol.LOGIN),
	LOGIN_ENCRYPTION_BEGIN(0x01, EnumProtocol.LOGIN),
	PLAY_KEEP_ALIVE(0x00, EnumProtocol.PLAY),
	PLAY_CHAT(0x01, EnumProtocol.PLAY),
	PLAY_USE_ENTITY(0x02, EnumProtocol.PLAY),
	PLAY_PLAYER(0x03, EnumProtocol.PLAY),
	PLAY_POSITION(0x04, EnumProtocol.PLAY),
	PLAY_LOOK(0x05, EnumProtocol.PLAY),
	PLAY_POSITION_LOOK(0x06, EnumProtocol.PLAY),
	PLAY_BLOCK_DIG(0x07, EnumProtocol.PLAY),
	PLAY_BLOCK_PLACE(0x08, EnumProtocol.PLAY),
	PLAY_HELD_SLOT(0x09, EnumProtocol.PLAY),
	PLAY_ANIMATION(0x0A, EnumProtocol.PLAY),
	PLAY_ENTITY_ACTION(0x0B, EnumProtocol.PLAY),
	PLAY_STEER_VEHICLE(0x0C, EnumProtocol.PLAY),
	PLAY_CLOSE_WINDOW(0x0D, EnumProtocol.PLAY),
	PLAY_WINDOW_CLICK(0x0E, EnumProtocol.PLAY),
	PLAY_WINDOW_TRANSACTION(0x0F, EnumProtocol.PLAY),
	PLAY_CREATIVE_SET_SLOT(0x10, EnumProtocol.PLAY),
	PLAY_ENCHANT_SELECT(0x11, EnumProtocol.PLAY),
	PLAY_UPDATE_SIGN(0x12, EnumProtocol.PLAY),
	PLAY_ABILITIES(0x13, EnumProtocol.PLAY),
	PLAY_TAB_COMPLETE(0x14, EnumProtocol.PLAY),
	PLAY_SETTINGS(0x15, EnumProtocol.PLAY),
	PLAY_CLIENT_COMMAND(0x16, EnumProtocol.PLAY),
	PLAY_CUSTOM_PAYLOAD(0x17, EnumProtocol.PLAY);

	private final int id;
	private final EnumProtocol protocol;
	ServerBoundPacket(int id, EnumProtocol protocol) {
		this.id = id;
		this.protocol = protocol;
	}

	public Packet<?> get() throws IllegalAccessException, InstantiationException {
		return protocol.a(EnumProtocolDirection.SERVERBOUND, id);
	}

	public static Packet<?> get(EnumProtocol protocol, int packetId) throws IllegalAccessException, InstantiationException {
		return protocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
	} 

}
