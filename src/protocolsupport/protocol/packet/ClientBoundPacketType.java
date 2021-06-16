package protocolsupport.protocol.packet;

import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public enum ClientBoundPacketType {

	CLIENTBOUND_LOGIN_DISCONNECT(ServerPlatform.get().getPacketFactory().getOutLoginDisconnectPacketId()),
	CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN(ServerPlatform.get().getPacketFactory().getOutLoginEncryptionBeginPacketId()),
	CLIENTBOUND_LOGIN_SUCCESS(ServerPlatform.get().getPacketFactory().getOutLoginSuccessPacketId()),
	CLIENTBOUND_LOGIN_SET_COMPRESSION(ServerPlatform.get().getPacketFactory().getOutLoginSetCompressionPacketId()),
	CLIENTBOUND_LOGIN_CUSTOM_PAYLOAD(ServerPlatform.get().getPacketFactory().getOutLoginCustomPayloadPacketId()),
	CLIENTBOUND_STATUS_SERVER_INFO(ServerPlatform.get().getPacketFactory().getOutStatusServerInfoPacketId()),
	CLIENTBOUND_STATUS_PONG(ServerPlatform.get().getPacketFactory().getOutStatusPongPacketId()),
	CLIENTBOUND_PLAY_KEEP_ALIVE(ServerPlatform.get().getPacketFactory().getOutPlayKeepAlivePacketId()),
	CLIENTBOUND_PLAY_START_GAME(ServerPlatform.get().getPacketFactory().getOutPlayLoginPacketId()),
	CLIENTBOUND_PLAY_CHAT(ServerPlatform.get().getPacketFactory().getOutPlayChatPacketId()),
	CLIENTBOUND_PLAY_UPDATE_TIME(ServerPlatform.get().getPacketFactory().getOutPlayUpdateTimePacketId()),
	CLIENTBOUND_PLAY_SPAWN_POSITION(ServerPlatform.get().getPacketFactory().getOutPlaySpawnPositionPacketId()),
	CLIENTBOUND_PLAY_SET_HEALTH(ServerPlatform.get().getPacketFactory().getOutPlayUpdateHealthPacketId()),
	CLIENTBOUND_PLAY_SET_EXPERIENCE(ServerPlatform.get().getPacketFactory().getOutPlayExperiencePacketId()),
	CLIENTBOUND_PLAY_SET_COOLDOWN(ServerPlatform.get().getPacketFactory().getOutPlaySetCooldownPacketId()),
	CLIENTBOUND_PLAY_RESPAWN(ServerPlatform.get().getPacketFactory().getOutPlayRespawnPacketId()),
	CLIENTBOUND_PLAY_POSITION(ServerPlatform.get().getPacketFactory().getOutPlayPositionPacketId()),
	CLIENTBOUND_PLAY_HELD_SLOT(ServerPlatform.get().getPacketFactory().getOutPlayHeldSlotPacketId()),
	CLIENTBOUND_PLAY_SPAWN_NAMED(ServerPlatform.get().getPacketFactory().getOutPlaySpawnNamedPacketId()),
	CLIENTBOUND_PLAY_COLLECT_EFFECT(ServerPlatform.get().getPacketFactory().getOutPlayCollectEffectPacketId()),
	CLIENTBOUND_PLAY_SPAWN_OBJECT(ServerPlatform.get().getPacketFactory().getOutPlaySpawnObjectPacketId()),
	CLIENTBOUND_PLAY_SPAWN_LIVING(ServerPlatform.get().getPacketFactory().getOutPlaySpawnLivingPacketId()),
	CLIENTBOUND_PLAY_SPAWN_PAINTING(ServerPlatform.get().getPacketFactory().getOutPlaySpawnPaintingPacketId()),
	CLIENTBOUND_PLAY_SPAWN_EXP_ORB(ServerPlatform.get().getPacketFactory().getOutPlaySpawnExpOrbPacketId()),
	CLIENTBOUND_PLAY_ENTITY_DESTROY(ServerPlatform.get().getPacketFactory().getOutPlayEntityDestroyPacketId()),
	CLIENTBOUND_PLAY_ENTITY_VELOCITY(ServerPlatform.get().getPacketFactory().getOutPlayEntityVelocityPacketId()),
	CLIENTBOUND_PLAY_ENTITY_REL_MOVE(ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMovePacketId()),
	CLIENTBOUND_PLAY_ENTITY_LOOK(ServerPlatform.get().getPacketFactory().getOutPlayEntityLookPacketId()),
	CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK(ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMoveLookPacketId()),
	CLIENTBOUND_PLAY_ENTITY_TELEPORT(ServerPlatform.get().getPacketFactory().getOutPlayEntityTeleportPacketId()),
	CLIENTBOUND_PLAY_ENTITY_HEAD_ROTATION(ServerPlatform.get().getPacketFactory().getOutPlayEntityHeadRotationPacketId()),
	CLIENTBOUND_PLAY_ENTITY_LEASH(ServerPlatform.get().getPacketFactory().getOutPlayEntityLeashPacketId()),
	CLIENTBOUND_PLAY_ENTITY_PASSENGERS(ServerPlatform.get().getPacketFactory().getOutPlaySetPassengersPacketId()),
	CLIENTBOUND_PLAY_ENTITY_METADATA(ServerPlatform.get().getPacketFactory().getOutPlayEntityMetadataPacketId()),
	CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES(ServerPlatform.get().getPacketFactory().getOutPlayEntityAttributesPacketId()),
	CLIENTBOUND_PLAY_ENTITY_STATUS(ServerPlatform.get().getPacketFactory().getOutPlayEntityStatusPacketId()),
	CLIENTBOUND_PLAY_ENTITY_ANIMATION(ServerPlatform.get().getPacketFactory().getOutPlayAnimationPacketId()),
	CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD(ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectAddPacketId()),
	CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE(ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectRemovePacketId()),
	CLIENTBOUND_PLAY_ENTITY_EQUIPMENT(ServerPlatform.get().getPacketFactory().getOutPlayEntityEquipmentPacketId()),
	CLIENTBOUND_PLAY_ENTITY_SOUND(ServerPlatform.get().getPacketFactory().getOutPlayEntitySoundPacketId()),
	CLIENTBOUND_PLAY_CHUNK_SINGLE(ServerPlatform.get().getPacketFactory().getOutPlayChunkSinglePacketId()),
	CLIENTBOUND_PLAY_CHUNK_LIGHT(ServerPlatform.get().getPacketFactory().getOutPlayChunkLightPacketId()),
	CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI(ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeMultiPacketId()),
	CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE(ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeSinglePacketId()),
	CLIENTBOUND_PLAY_BLOCK_TILE(ServerPlatform.get().getPacketFactory().getOutPlayUpdateTilePacketId()),
	CLIENTBOUND_PLAY_BLOCK_ACTION(ServerPlatform.get().getPacketFactory().getOutPlayBlockActionPacketId()),
	CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION(ServerPlatform.get().getPacketFactory().getOutPlayBlockBreakAnimationPacketId()),
	CLIENTBOUND_PLAY_EXPLOSION(ServerPlatform.get().getPacketFactory().getOutPlayExplosionPacketId()),
	CLIENTBOUND_PLAY_WORLD_EVENT(ServerPlatform.get().getPacketFactory().getOutPlayWorldEventPacketId()),
	CLIENTBOUND_PLAY_WORLD_SOUND(ServerPlatform.get().getPacketFactory().getOutPlayWorldSoundPacketId()),
	CLIENTBOUND_PLAY_WORLD_PARTICLES(ServerPlatform.get().getPacketFactory().getOutPlayWorldParticlesPacketId()),
	CLIENTBOUND_PLAY_GAME_STATE_CHANGE(ServerPlatform.get().getPacketFactory().getOutPlayGameStateChangePacketId()),
	CLIENTBOUND_PLAY_WINDOW_OPEN(ServerPlatform.get().getPacketFactory().getOutPlayWindowOpenPacketId()),
	CLIENTBOUND_PLAY_WINDOW_HORSE_OPEN(ServerPlatform.get().getPacketFactory().getOutPlayWindowHorseOpenPacketId()),
	CLIENTBOUND_PLAY_WINDOW_CLOSE(ServerPlatform.get().getPacketFactory().getOutPlayWindowClosePacketId()),
	CLIENTBOUND_PLAY_WINDOW_SET_SLOT(ServerPlatform.get().getPacketFactory().getOutPlayWindowSetSlotPacketId()),
	CLIENTBOUND_PLAY_WINDOW_SET_ITEMS(ServerPlatform.get().getPacketFactory().getOutPlayWindowSetItemsPacketId()),
	CLIENTBOUND_PLAY_WINDOW_DATA(ServerPlatform.get().getPacketFactory().getOutPlayWindowDataPacketId()),
	CLIENTBOUND_PLAY_UPDATE_MAP(ServerPlatform.get().getPacketFactory().getOutPlayMapPacketId()),
	CLIENTBOUND_PLAY_SIGN_EDITOR(ServerPlatform.get().getPacketFactory().getOutPlaySignEditorPacketId()),
	CLIENTBOUND_PLAY_STATISTICS(ServerPlatform.get().getPacketFactory().getOutPlayStatisticsPacketId()),
	CLIENTBOUND_PLAY_PLAYER_INFO(ServerPlatform.get().getPacketFactory().getOutPlayPlayerInfoPacketId()),
	CLIENTBOUND_PLAY_PLAYER_ABILITIES(ServerPlatform.get().getPacketFactory().getOutPlayAbilitiesPacketId()),
	CLIENTBOUND_PLAY_TAB_COMPLETE(ServerPlatform.get().getPacketFactory().getOutPlayTabCompletePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE(ServerPlatform.get().getPacketFactory().getOutPlayScoreboardObjectivePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_SCORE(ServerPlatform.get().getPacketFactory().getOutPlayScoreboardScorePacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT(ServerPlatform.get().getPacketFactory().getOutPlayScoreboardDisplaySlotPacketId()),
	CLIENTBOUND_PLAY_SCOREBOARD_TEAM(ServerPlatform.get().getPacketFactory().getOutPlayScoreboardTeamPacketId()),
	CLIENTBOUND_PLAY_CUSTOM_PAYLOAD(ServerPlatform.get().getPacketFactory().getOutPlayCustomPayloadPacketId()),
	CLIENTBOUND_PLAY_KICK_DISCONNECT(ServerPlatform.get().getPacketFactory().getOutPlayKickDisconnectPacketId()),
	CLIENTBOUND_PLAY_RESOURCE_PACK(ServerPlatform.get().getPacketFactory().getOutPlayResourcePackPacketId()),
	CLIENTBOUND_PLAY_CAMERA(ServerPlatform.get().getPacketFactory().getOutPlayCameraPacketId()),
	CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER(ServerPlatform.get().getPacketFactory().getOutPlayPlayerListHeaderFooterPacketId()),
	CLIENTBOUND_PLAY_CHUNK_UNLOAD(ServerPlatform.get().getPacketFactory().getOutPlayChunkUnloadPacketId()),
	CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND(ServerPlatform.get().getPacketFactory().getOutPlayWorldCustomSoundPacketId()),
	CLIENTBOUND_PLAY_SERVER_DIFFICULTY(ServerPlatform.get().getPacketFactory().getOutPlayServerDifficultyPacketId()),
	CLIENTBOUND_PLAY_BOSS_BAR(ServerPlatform.get().getPacketFactory().getOutPlayBossBarPacketId()),
	CLIENTBOUND_PLAY_VEHICLE_MOVE(ServerPlatform.get().getPacketFactory().getOutPlayVehicleMovePacketId()),
	CLIENTBOUND_PLAY_UNLOCK_RECIPES(ServerPlatform.get().getPacketFactory().getOutPlayUnlockRecipesPacketId()),
	CLIENTBOUND_PLAY_ADVANCEMENTS(ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsPacketId()),
	CLIENTBOUND_PLAY_ADVANCEMENTS_TAB(ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsTabPacketId()),
	CLIENTBOUND_PLAY_CRAFT_RECIPE_CONFIRM(ServerPlatform.get().getPacketFactory().getOutPlayCraftRecipeConfirmPacketId()),
	CLIENTBOUND_PLAY_DECLARE_COMMANDS(ServerPlatform.get().getPacketFactory().getOutPlayDeclareCommandsPacketId()),
	CLIENTBOUND_PLAY_DECLARE_RECIPES(ServerPlatform.get().getPacketFactory().getOutPlayDeclareRecipesPacketId()),
	CLIENTBOUND_PLAY_DECLARE_TAGS(ServerPlatform.get().getPacketFactory().getOutPlayDeclareTagsPacket()),
	CLIENTBOUND_PLAY_QUERY_NBT_RESPONSE(ServerPlatform.get().getPacketFactory().getOutPlayQueryNBTResponsePacketId()),
	CLIENTBOUND_PLAY_STOP_SOUND(ServerPlatform.get().getPacketFactory().getOutPlayStopSoundPacketId()),
	CLIENTBOUND_PLAY_LOOK_AT(ServerPlatform.get().getPacketFactory().getOutPlayLookAtPacketId()),
	CLIENTBOUND_PLAY_SET_VIEW_CENTER(ServerPlatform.get().getPacketFactory().getOutPlaySetViewCenterPacketId()),
	CLIENTBOUND_PLAY_UPDATE_VIEW_DISTANCE(ServerPlatform.get().getPacketFactory().getOutPlayUpdateViewDistancePacketId()),
	CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST(ServerPlatform.get().getPacketFactory().getOutPlayMerchantTradeListPacketId()),
	CLIENTBOUND_PLAY_BOOK_OPEN(ServerPlatform.get().getPacketFactory().getOutPlayBookOpenPacketId()),
	CLIENTBOUND_PLAY_BLOCK_BREAK_CONFIRM(ServerPlatform.get().getPacketFactory().getOutPlayAcknowledgePlayerDiggingPacketId()),
	CLIENTBOUND_PLAY_TITLE_TEXT(ServerPlatform.get().getPacketFactory().getOutPlayTitleTextPacketId()),
	CLIENTBOUND_PLAY_TITLE_SUBTEXT(ServerPlatform.get().getPacketFactory().getOutPlayTitleSubTextPacketId()),
	CLIENTBOUND_PLAY_TITLE_ANIMATION(ServerPlatform.get().getPacketFactory().getOutPlayTitleAnimationPacketId()),
	CLIENTBOUND_PLAY_TITLE_CLEAR(ServerPlatform.get().getPacketFactory().getOutPlayTitleClearPacketId()),
	CLIENTBOUND_PLAY_ACTIONBAR(ServerPlatform.get().getPacketFactory().getOutPlayActionbarPacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_INIT(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderInitPacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_CENTER(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderCenterPacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_LERP_SIZE(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderLerpSizePacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_SIZE(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderSizePacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_WARN_DELAY(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderWarnDelayPacketId()),
	CLIENTBOUND_PLAY_WORLDBORDER_WARN_DISTANCE(ServerPlatform.get().getPacketFactory().getOutPlayWorldborderWarnDistancePacketId()),
	CLIENTBOUND_PLAY_COMBAT_BEGIN(ServerPlatform.get().getPacketFactory().getOutPlayCombatBeginPacketId()),
	CLIENTBOUND_PLAY_COMBAT_END(ServerPlatform.get().getPacketFactory().getOutPlayCombatEndPacketId()),
	CLIENTBOUND_PLAY_COMBAT_DEATH(ServerPlatform.get().getPacketFactory().getOutPlayCombatDeathPacketId()),
	CLIENTBOUND_PLAY_VIBRATION(ServerPlatform.get().getPacketFactory().getOutPlayVibration()),
	CLIENTBOUND_PLAY_SYNC_PING(ServerPlatform.get().getPacketFactory().getOutPlaySyncPing()),

	CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN(-1),
	CLIENTBOUND_LEGACY_PLAY_USE_BED(-1),
	CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL(-1),
	CLIENTBOUND_LEGACY_PLAY_WINDOW_TRANSACTION(-1);

	private final int id;

	private ClientBoundPacketType(int id) {
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
