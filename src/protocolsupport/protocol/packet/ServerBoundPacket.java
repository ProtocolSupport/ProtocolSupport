package protocolsupport.protocol.packet;

import protocolsupport.zplatform.ServerPlatform;

public enum ServerBoundPacket {

	HANDSHAKE_START(ServerPlatform.get().getPacketFactory().getInHandshakeStartPacketId()),
	STATUS_REQUEST(ServerPlatform.get().getPacketFactory().getInStatusRequestPacketId()),
	STATUS_PING(ServerPlatform.get().getPacketFactory().getInStatusPingPacketId()),
	LOGIN_START(ServerPlatform.get().getPacketFactory().getInLoginStartPacketId()),
	LOGIN_ENCRYPTION_BEGIN(ServerPlatform.get().getPacketFactory().getInLoginEncryptionBeginPacketId()),
	PLAY_KEEP_ALIVE(ServerPlatform.get().getPacketFactory().getInPlayKeepAlivePacketId()),
	PLAY_CHAT(ServerPlatform.get().getPacketFactory().getInPlayChatPacketId()),
	PLAY_USE_ENTITY(ServerPlatform.get().getPacketFactory().getInPlayUseEntityPacketId()),
	PLAY_PLAYER(ServerPlatform.get().getPacketFactory().getInPlayPlayerPacketId()),
	PLAY_POSITION(ServerPlatform.get().getPacketFactory().getInPlayPositionPacketId()),
	PLAY_LOOK(ServerPlatform.get().getPacketFactory().getInPlayLookPacketId()),
	PLAY_POSITION_LOOK(ServerPlatform.get().getPacketFactory().getInPlayPositionLookPacketId()),
	PLAY_BLOCK_DIG(ServerPlatform.get().getPacketFactory().getInPlayBlockDigPacketId()),
	PLAY_BLOCK_PLACE(ServerPlatform.get().getPacketFactory().getInPlayBlockPlacePacketId()),
	PLAY_HELD_SLOT(ServerPlatform.get().getPacketFactory().getInPlayHeldSlotPacketId()),
	PLAY_ANIMATION(ServerPlatform.get().getPacketFactory().getInPlayAnimationPacketId()),
	PLAY_ENTITY_ACTION(ServerPlatform.get().getPacketFactory().getInPlayEntityActionPacketId()),
	PLAY_MOVE_VEHICLE(ServerPlatform.get().getPacketFactory().getInPlayMoveVehiclePacketId()),
	PLAY_STEER_BOAT(ServerPlatform.get().getPacketFactory().getInPlaySteerBoatPacketId()),
	PLAY_STEER_VEHICLE(ServerPlatform.get().getPacketFactory().getInPlaySteerVehiclePacketId()),
	PLAY_WINDOW_CLOSE(ServerPlatform.get().getPacketFactory().getInPlayWindowClosePacketId()),
	PLAY_WINDOW_CLICK(ServerPlatform.get().getPacketFactory().getInPlayWindowClickPacketId()),
	PLAY_WINDOW_TRANSACTION(ServerPlatform.get().getPacketFactory().getInPlayWindowTransactionPacketId()),
	PLAY_CREATIVE_SET_SLOT(ServerPlatform.get().getPacketFactory().getInPlayCreativeSetSlotPacketId()),
	PLAY_ENCHANT_SELECT(ServerPlatform.get().getPacketFactory().getInPlayEnchantSelectPacketId()),
	PLAY_UPDATE_SIGN(ServerPlatform.get().getPacketFactory().getInPlayUpdateSignPacketId()),
	PLAY_ABILITIES(ServerPlatform.get().getPacketFactory().getInPlayAbilitiesPacketId()),
	PLAY_TAB_COMPLETE(ServerPlatform.get().getPacketFactory().getInPlayTabCompletePacketId()),
	PLAY_SETTINGS(ServerPlatform.get().getPacketFactory().getInPlaySettingsPacketId()),
	PLAY_CLIENT_COMMAND(ServerPlatform.get().getPacketFactory().getInPlayClientCommandPacketId()),
	PLAY_CUSTOM_PAYLOAD(ServerPlatform.get().getPacketFactory().getInPlayCustomPayloadPacketId()),
	PLAY_USE_ITEM(ServerPlatform.get().getPacketFactory().getInPlayUseItemPacketId()),
	PLAY_SPECTATE(ServerPlatform.get().getPacketFactory().getInPlaySpectatePacketId()),
	PLAY_RESOURCE_PACK_STATUS(ServerPlatform.get().getPacketFactory().getInPlayResourcePackStatusPacketId()),
	PLAY_TELEPORT_ACCEPT(ServerPlatform.get().getPacketFactory().getInPlayTeleportAcceptPacketId()),
	PLAY_CRAFTING_BOOK_DATA(ServerPlatform.get().getPacketFactory().getInPlayCraftingBookPacketId()),
	PLAY_PREPARE_CRAFTING_GRID(ServerPlatform.get().getPacketFactory().getInPlayPrepareCraftingGridPacketId()),
	PLAY_ADVANCEMENT_TAB(ServerPlatform.get().getPacketFactory().getInPlayAdvancementTabPacketId());

	private final int id;
	ServerBoundPacket(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
