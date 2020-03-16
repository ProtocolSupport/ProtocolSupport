package protocolsupport.protocol.packet;

import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public enum PacketType {

	NONE(Direction.NONE, -1),
	SERVERBOUND_HANDSHAKE_START(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInHandshakeStartPacketId()),
	SERVERBOUND_STATUS_REQUEST(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInStatusRequestPacketId()),
	SERVERBOUND_STATUS_PING(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInStatusPingPacketId()),
	SERVERBOUND_LOGIN_START(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginStartPacketId()),
	SERVERBOUND_LOGIN_ENCRYPTION_BEGIN(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginEncryptionBeginPacketId()),
	SERVERBOUND_LOGIN_CUSTOM_PAYLOAD(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginCustomPayloadPacketId()),
	SERVERBOUND_PLAY_KEEP_ALIVE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayKeepAlivePacketId()),
	SERVERBOUND_PLAY_CHAT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayChatPacketId()),
	SERVERBOUND_PLAY_USE_ENTITY(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUseEntityPacketId()),
	SERVERBOUND_PLAY_PLAYER(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPlayerPacketId()),
	SERVERBOUND_PLAY_POSITION(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPositionPacketId()),
	SERVERBOUND_PLAY_LOOK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayLookPacketId()),
	SERVERBOUND_PLAY_POSITION_LOOK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPositionLookPacketId()),
	SERVERBOUND_PLAY_BLOCK_DIG(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayBlockDigPacketId()),
	SERVERBOUND_PLAY_BLOCK_PLACE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayBlockPlacePacketId()),
	SERVERBOUND_PLAY_HELD_SLOT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayHeldSlotPacketId()),
	SERVERBOUND_PLAY_ANIMATION(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAnimationPacketId()),
	SERVERBOUND_PLAY_ENTITY_ACTION(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEntityActionPacketId()),
	SERVERBOUND_PLAY_MOVE_VEHICLE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayMoveVehiclePacketId()),
	SERVERBOUND_PLAY_STEER_BOAT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySteerBoatPacketId()),
	SERVERBOUND_PLAY_STEER_VEHICLE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySteerVehiclePacketId()),
	SERVERBOUND_PLAY_WINDOW_CLOSE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowClosePacketId()),
	SERVERBOUND_PLAY_WINDOW_CLICK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowClickPacketId()),
	SERVERBOUND_PLAY_WINDOW_TRANSACTION(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowTransactionPacketId()),
	SERVERBOUND_PLAY_CREATIVE_SET_SLOT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCreativeSetSlotPacketId()),
	SERVERBOUND_PLAY_ENCHANT_SELECT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEnchantSelectPacketId()),
	SERVERBOUND_PLAY_UPDATE_SIGN(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateSignPacketId()),
	SERVERBOUND_PLAY_ABILITIES(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAbilitiesPacketId()),
	SERVERBOUND_PLAY_TAB_COMPLETE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayTabCompletePacketId()),
	SERVERBOUND_PLAY_SETTINGS(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySettingsPacketId()),
	SERVERBOUND_PLAY_CLIENT_COMMAND(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayClientCommandPacketId()),
	SERVERBOUND_PLAY_CUSTOM_PAYLOAD(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCustomPayloadPacketId()),
	SERVERBOUND_PLAY_USE_ITEM(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUseItemPacketId()),
	SERVERBOUND_PLAY_SPECTATE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySpectatePacketId()),
	SERVERBOUND_PLAY_RESOURCE_PACK_STATUS(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayResourcePackStatusPacketId()),
	SERVERBOUND_PLAY_TELEPORT_ACCEPT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayTeleportAcceptPacketId()),
	SERVERBOUND_PLAY_RECIPE_BOOK_DATA(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayRecipeBookDataPacketId()),
	SERVERBOUND_PLAY_CRAFT_RECIPE_REQUEST(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCraftRecipeRequestPacketId()),
	SERVERBOUND_PLAY_ADVANCEMENT_TAB(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAdvancementTabPacketId()),
	SERVERBOUND_PLAY_QUERY_BLOCK_NBT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayQueryBlockNBTPacketId()),
	SERVERBOUND_PLAY_QUERY_ENTITY_NBT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayQueryEntityNBTPacketId()),
	SERVERBOUND_PLAY_EDIT_BOOK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEditBookPacketId()),
	SERVERBOUND_PLAY_PICK_ITEM(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPickItemPacketId()),
	SERVERBOUND_PLAY_NAME_ITEM(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayNameItemPacketId()),
	SERVERBOUND_PLAY_SELECT_TRADE(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySelectTradePacketId()),
	SERVERBOUND_PLAY_SET_BEACON_EFFECT(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySetBeaconEffectPacketId()),
	SERVERBOUND_PLAY_UPDATE_COMMAND_BLOCK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandBlockPacketId()),
	SERVERBOUND_PLAY_UPDATE_COMMAND_MINECART(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandMinecartPacketId()),
	SERVERBOUND_PLAY_UPDATE_STRUCTURE_BLOCK(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateStructureBlockPacketId()),

	CLIENTBOUND_LOGIN_DISCONNECT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginDisconnectPacketId()),
	CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginEncryptionBeginPacketId()),
	CLIENTBOUND_LOGIN_SUCCESS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginSuccessPacketId()),
	CLIENTBOUND_LOGIN_SET_COMPRESSION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginSetCompressionPacketId()),
	CLIENTBOUND_LOGIN_CUSTOM_PAYLOAD(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginCustomPayloadPacketId()),
	CLIENTBOUND_STATUS_SERVER_INFO(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutStatusServerInfoPacketId()),
	CLIENTBOUND_STATUS_PONG(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutStatusPongPacketId()),
	CLIENTBOUND_PLAY_KEEP_ALIVE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayKeepAlivePacketId()),
	CLIENTBOUND_PLAY_START_GAME(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayLoginPacketId()),
	CLIENTBOUND_PLAY_CHAT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChatPacketId()),
	CLIENTBOUND_PLAY_UPDATE_TIME(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateTimePacketId()),
	CLIENTBOUND_PLAY_SPAWN_POSITION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnPositionPacketId()),
	CLIENTBOUND_PLAY_SET_HEALTH(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateHealthPacketId()),
	CLIENTBOUND_PLAY_SET_EXPERIENCE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayExperiencePacketId()),
	CLIENTBOUND_PLAY_SET_COOLDOWN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetCooldownPacketId()),
	CLIENTBOUND_PLAY_RESPAWN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayRespawnPacketId()),
	CLIENTBOUND_PLAY_POSITION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPositionPacketId()),
	CLIENTBOUND_PLAY_HELD_SLOT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayHeldSlotPacketId()),
	CLIENTBOUND_PLAY_SPAWN_NAMED(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnNamedPacketId()),
	CLIENTBOUND_PLAY_COLLECT_EFFECT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCollectEffectPacketId()),
	CLIENTBOUND_PLAY_SPAWN_OBJECT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnObjectPacketId()),
	CLIENTBOUND_PLAY_SPAWN_LIVING(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnLivingPacketId()),
	CLIENTBOUND_PLAY_SPAWN_PAINTING(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnPaintingPacketId()),
	CLIENTBOUND_PLAY_SPAWN_EXP_ORB(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnExpOrbPacketId()),
	CLIENTBOUND_PLAY_SPAWN_GLOBAL(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnWeatherPacketId()),
	CLIENTBOUND_PLAY_ENTITY_DESTROY(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityDestroyPacketId()),
	CLIENTBOUND_PLAY_ENTITY_VELOCITY(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityVelocityPacketId()),
	CLIENTBOUND_PLAY_ENTITY_NOOP(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityPacketId()),
	CLIENTBOUND_PLAY_ENTITY_REL_MOVE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMovePacketId()),
	CLIENTBOUND_PLAY_ENTITY_LOOK(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityLookPacketId()),
	CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMoveLookPacketId()),
	CLIENTBOUND_PLAY_ENTITY_TELEPORT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityTeleportPacketId()),
	CLIENTBOUND_PLAY_ENTITY_HEAD_ROTATION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityHeadRotationPacketId()),
	CLIENTBOUND_PLAY_ENTITY_LEASH(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityLeashPacketId()),
	CLIENTBOUND_PLAY_ENTITY_PASSENGERS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetPassengersPacketId()),
	CLIENTBOUND_PLAY_ENTITY_METADATA(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityMetadataPacketId()),
	CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityAttributesPacketId()),
	CLIENTBOUND_PLAY_ENTITY_STATUS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityStatusPacketId()),
	CLIENTBOUND_PLAY_ENTITY_ANIMATION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAnimationPacketId()),
	CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectAddPacketId()),
	CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectRemovePacketId()),
	CLIENTBOUND_PLAY_ENTITY_EQUIPMENT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEquipmentPacketId()),
	CLIENTBOUND_PLAY_ENTITY_SOUND(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntitySoundPacketId()),
	CLIENTBOUND_PLAY_CHUNK_SINGLE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkSinglePacketId()),
	CLIENTBOUND_PLAY_CHUNK_LIGHT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkLightPacketId()),
	CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeMultiPacketId()),
	CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeSinglePacketId()),
	CLIENTBOUND_PLAY_BLOCK_TILE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateTilePacketId()),
	CLIENTBOUND_PLAY_BLOCK_ACTION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockActionPacketId()),
	CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockBreakAnimationPacketId()),
	CLIENTBOUND_PLAY_EXPLOSION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayExplosionPacketId()),
	CLIENTBOUND_PLAY_WORLD_EVENT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldEventPacketId()),
	CLIENTBOUND_PLAY_WORLD_SOUND(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldSoundPacketId()),
	CLIENTBOUND_PLAY_WORLD_PARTICLES(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldParticlesPacketId()),
	CLIENTBOUND_PLAY_GAME_STATE_CHANGE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayGameStateChangePacketId()),
	CLIENTBOUND_PLAY_WINDOW_OPEN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowOpenPacketId()),
	CLIENTBOUND_PLAY_WINDOW_HORSE_OPEN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowHorseOpenPacketId()),
	CLIENTBOUND_PLAY_WINDOW_CLOSE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowClosePacketId()),
	CLIENTBOUND_PLAY_WINDOW_SET_SLOT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowSetSlotPacketId()),
	CLIENTBOUND_PLAY_WINDOW_SET_ITEMS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowSetItemsPacketId()),
	CLIENTBOUND_PLAY_WINDOW_DATA(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowDataPacketId()),
	CLIENTBOUND_PLAY_WINDOW_TRANSACTION(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowTransactionPacketId()),
	CLIENTBOUND_PLAY_UPDATE_MAP(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayMapPacketId()),
	CLIENTBOUND_PLAY_SIGN_EDITOR(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySignEditorPacketId()),
	CLIENTBOUND_PLAY_STATISTICS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayStatisticsPacketId()),
	CLIENTBOUND_PLAY_PLAYER_INFO(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPlayerInfoPacketId()),
	CLIENTBOUND_PLAY_PLAYER_ABILITIES(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAbilitiesPacketId()),
	CLIENTBOUND_PLAY_TAB_COMPLETE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayTabCompletePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardObjectivePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_SCORE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardScorePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardDisplaySlotPacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_TEAM(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardTeamPacketId()),
	CLIENTBOUND_PLAY_CUSTOM_PAYLOAD(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCustomPayloadPacketId()),
	CLIENTBOUND_PLAY_KICK_DISCONNECT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayKickDisconnectPacketId()),
	CLIENTBOUND_PLAY_RESOURCE_PACK(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayResourcePackPacketId()),
	CLIENTBOUND_PLAY_CAMERA(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCameraPacketId()),
	CLIENTBOUND_PLAY_WORLD_BORDER(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldBorderPacketId()),
	CLIENTBOUND_PLAY_TITLE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayTitlePacketId()),
	CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPlayerListHeaderFooterPacketId()),
	CLIENTBOUND_PLAY_CHUNK_UNLOAD(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkUnloadPacketId()),
	CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldCustomSoundPacketId()),
	CLIENTBOUND_PLAY_SERVER_DIFFICULTY(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayServerDifficultyPacketId()),
	CLIENTBOUND_PLAY_COMBAT_EVENT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCombatEventPacketId()),
	CLIENTBOUND_PLAY_BOSS_BAR(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBossBarPacketId()),
	CLIENTBOUND_PLAY_VEHICLE_MOVE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayVehicleMovePacketId()),
	CLIENTBOUND_PLAY_UNLOCK_RECIPES(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUnlockRecipesPacketId()),
	CLIENTBOUND_PLAY_ADVANCEMENTS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsPacketId()),
	CLIENTBOUND_PLAY_ADVANCEMENTS_TAB(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsTabPacketId()),
	CLIENTBOUND_PLAY_CRAFT_RECIPE_CONFIRM(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCraftRecipeConfirmPacketId()),
	CLIENTBOUND_PLAY_DECLARE_COMMANDS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareCommandsPacketId()),
	CLIENTBOUND_PLAY_DECLARE_RECIPES(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareRecipesPacketId()),
	CLIENTBOUND_PLAY_DECLARE_TAGS(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareTagsPacket()),
	CLIENTBOUND_PLAY_QUERY_NBT_RESPONSE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayQueryNBTResponsePacketId()),
	CLIENTBOUND_PLAY_STOP_SOUND(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayStopSoundPacketId()),
	CLIENTBOUND_PLAY_LOOK_AT(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayLookAtPacketId()),
	CLIENTBOUND_PLAY_SET_VIEW_CENTER(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetViewCenterPacketId()),
	CLIENTBOUND_PLAY_UPDATE_VIEW_DISTANCE(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateViewDistancePacketId()),
	CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayMerchantTradeListPacketId()),
	CLIENTBOUND_PLAY_BOOK_OPEN(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBookOpenPacketId()),
	CLIENTBOUND_PLAY_BLOCK_BREAK_CONFIRM(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAcknowledgePlayerDiggingId()),

	CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID(Direction.CLIENTBOUND, -1),
	CLIENTBOUND_LEGACY_PLAY_USE_BED_ID(Direction.CLIENTBOUND, -1);

	private final Direction direction;
	private final int id;

	private PacketType(Direction direction, int id) {
		this.direction = direction;
		this.id = id;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getId() {
		return id;
	}

	public static enum Direction {
		CLIENTBOUND, SERVERBOUND, NONE
	}


	private static final int count = values().length;

	public static int getValuesCount() {
		return count;
	}

}
