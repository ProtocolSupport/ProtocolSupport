package protocolsupport.protocol.packet;

import protocolsupport.zplatform.ServerPlatform;

public enum ServerBoundPacketType {

	SERVERBOUND_HANDSHAKE_START(ServerPlatform.get().getPacketFactory().getInHandshakeStartPacketId()),
	SERVERBOUND_STATUS_REQUEST(ServerPlatform.get().getPacketFactory().getInStatusRequestPacketId()),
	SERVERBOUND_STATUS_PING(ServerPlatform.get().getPacketFactory().getInStatusPingPacketId()),
	SERVERBOUND_LOGIN_START(ServerPlatform.get().getPacketFactory().getInLoginStartPacketId()),
	SERVERBOUND_LOGIN_ENCRYPTION_BEGIN(ServerPlatform.get().getPacketFactory().getInLoginEncryptionBeginPacketId()),
	SERVERBOUND_LOGIN_CUSTOM_PAYLOAD(ServerPlatform.get().getPacketFactory().getInLoginCustomPayloadPacketId()),
	SERVERBOUND_PLAY_KEEP_ALIVE(ServerPlatform.get().getPacketFactory().getInPlayKeepAlivePacketId()),
	SERVERBOUND_PLAY_CHAT(ServerPlatform.get().getPacketFactory().getInPlayChatPacketId()),
	SERVERBOUND_PLAY_USE_ENTITY(ServerPlatform.get().getPacketFactory().getInPlayUseEntityPacketId()),
	SERVERBOUND_PLAY_POSITION(ServerPlatform.get().getPacketFactory().getInPlayPositionPacketId()),
	SERVERBOUND_PLAY_LOOK(ServerPlatform.get().getPacketFactory().getInPlayLookPacketId()),
	SERVERBOUND_PLAY_POSITION_LOOK(ServerPlatform.get().getPacketFactory().getInPlayPositionLookPacketId()),
	SERVERBOUND_PLAY_BLOCK_DIG(ServerPlatform.get().getPacketFactory().getInPlayBlockDigPacketId()),
	SERVERBOUND_PLAY_BLOCK_PLACE(ServerPlatform.get().getPacketFactory().getInPlayBlockPlacePacketId()),
	SERVERBOUND_PLAY_HELD_SLOT(ServerPlatform.get().getPacketFactory().getInPlayHeldSlotPacketId()),
	SERVERBOUND_PLAY_ANIMATION(ServerPlatform.get().getPacketFactory().getInPlayAnimationPacketId()),
	SERVERBOUND_PLAY_ENTITY_ACTION(ServerPlatform.get().getPacketFactory().getInPlayEntityActionPacketId()),
	SERVERBOUND_PLAY_MOVE_VEHICLE(ServerPlatform.get().getPacketFactory().getInPlayMoveVehiclePacketId()),
	SERVERBOUND_PLAY_STEER_BOAT(ServerPlatform.get().getPacketFactory().getInPlaySteerBoatPacketId()),
	SERVERBOUND_PLAY_STEER_VEHICLE(ServerPlatform.get().getPacketFactory().getInPlaySteerVehiclePacketId()),
	SERVERBOUND_PLAY_WINDOW_CLOSE(ServerPlatform.get().getPacketFactory().getInPlayWindowClosePacketId()),
	SERVERBOUND_PLAY_WINDOW_CLICK(ServerPlatform.get().getPacketFactory().getInPlayWindowClickPacketId()),
	SERVERBOUND_PLAY_CREATIVE_SET_SLOT(ServerPlatform.get().getPacketFactory().getInPlayCreativeSetSlotPacketId()),
	SERVERBOUND_PLAY_ENCHANT_SELECT(ServerPlatform.get().getPacketFactory().getInPlayEnchantSelectPacketId()),
	SERVERBOUND_PLAY_UPDATE_SIGN(ServerPlatform.get().getPacketFactory().getInPlayUpdateSignPacketId()),
	SERVERBOUND_PLAY_ABILITIES(ServerPlatform.get().getPacketFactory().getInPlayAbilitiesPacketId()),
	SERVERBOUND_PLAY_TAB_COMPLETE(ServerPlatform.get().getPacketFactory().getInPlayTabCompletePacketId()),
	SERVERBOUND_PLAY_SETTINGS(ServerPlatform.get().getPacketFactory().getInPlaySettingsPacketId()),
	SERVERBOUND_PLAY_CLIENT_COMMAND(ServerPlatform.get().getPacketFactory().getInPlayClientCommandPacketId()),
	SERVERBOUND_PLAY_CUSTOM_PAYLOAD(ServerPlatform.get().getPacketFactory().getInPlayCustomPayloadPacketId()),
	SERVERBOUND_PLAY_USE_ITEM(ServerPlatform.get().getPacketFactory().getInPlayUseItemPacketId()),
	SERVERBOUND_PLAY_SPECTATE(ServerPlatform.get().getPacketFactory().getInPlaySpectatePacketId()),
	SERVERBOUND_PLAY_RESOURCE_PACK_STATUS(ServerPlatform.get().getPacketFactory().getInPlayResourcePackStatusPacketId()),
	SERVERBOUND_PLAY_TELEPORT_ACCEPT(ServerPlatform.get().getPacketFactory().getInPlayTeleportAcceptPacketId()),
	SERVERBOUND_PLAY_RECIPE_BOOK_RECIPE(ServerPlatform.get().getPacketFactory().getInPlayRecipeBookRecipePacketId()),
	SERVERBOUND_PLAY_RECIPE_BOOK_STATE(ServerPlatform.get().getPacketFactory().getInPlayRecipeBookStatePacketId()),
	SERVERBOUND_PLAY_CRAFT_RECIPE_REQUEST(ServerPlatform.get().getPacketFactory().getInPlayCraftRecipeRequestPacketId()),
	SERVERBOUND_PLAY_ADVANCEMENT_TAB(ServerPlatform.get().getPacketFactory().getInPlayAdvancementTabPacketId()),
	SERVERBOUND_PLAY_QUERY_BLOCK_NBT(ServerPlatform.get().getPacketFactory().getInPlayQueryBlockNBTPacketId()),
	SERVERBOUND_PLAY_QUERY_ENTITY_NBT(ServerPlatform.get().getPacketFactory().getInPlayQueryEntityNBTPacketId()),
	SERVERBOUND_PLAY_EDIT_BOOK(ServerPlatform.get().getPacketFactory().getInPlayEditBookPacketId()),
	SERVERBOUND_PLAY_PICK_ITEM(ServerPlatform.get().getPacketFactory().getInPlayPickItemPacketId()),
	SERVERBOUND_PLAY_NAME_ITEM(ServerPlatform.get().getPacketFactory().getInPlayNameItemPacketId()),
	SERVERBOUND_PLAY_SELECT_TRADE(ServerPlatform.get().getPacketFactory().getInPlaySelectTradePacketId()),
	SERVERBOUND_PLAY_SET_BEACON_EFFECT(ServerPlatform.get().getPacketFactory().getInPlaySetBeaconEffectPacketId()),
	SERVERBOUND_PLAY_UPDATE_COMMAND_BLOCK(ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandBlockPacketId()),
	SERVERBOUND_PLAY_UPDATE_COMMAND_MINECART(ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandMinecartPacketId()),
	SERVERBOUND_PLAY_UPDATE_STRUCTURE_BLOCK(ServerPlatform.get().getPacketFactory().getInPlayUpdateStructureBlockPacketId()),
	SERVERBOUND_PLAY_JIGSAW_UPDATE(ServerPlatform.get().getPacketFactory().getInPlayJigsawUpdatePacketId()),
	SERVERBOUND_PLAY_JIGSAW_GENERATE(ServerPlatform.get().getPacketFactory().getInPlayJigsawGenerateStructurePacketId()),
	SERVERBOUND_PLAY_SYNC_PONG(ServerPlatform.get().getPacketFactory().getInPlaySyncPong());

	private final int id;

	private ServerBoundPacketType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	private static final int count = values().length;

	public static int getValuesCount() {
		return count;
	}

}
