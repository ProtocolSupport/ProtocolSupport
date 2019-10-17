package protocolsupport.protocol.packet;

import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PacketType {

	private static int ordinalCtr;

	public static int getCount() {
		return ordinalCtr;
	}

	public static final PacketType NONE = new PacketType(Direction.NONE, -1);
	public static final PacketType SERVERBOUND_HANDSHAKE_START = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInHandshakeStartPacketId());
	public static final PacketType SERVERBOUND_STATUS_REQUEST = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInStatusRequestPacketId());
	public static final PacketType SERVERBOUND_STATUS_PING = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInStatusPingPacketId());
	public static final PacketType SERVERBOUND_LOGIN_START = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginStartPacketId());
	public static final PacketType SERVERBOUND_LOGIN_ENCRYPTION_BEGIN = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginEncryptionBeginPacketId());
	public static final PacketType SERVERBOUND_LOGIN_CUSTOM_PAYLOAD = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInLoginCustomPayloadPacketId());
	public static final PacketType SERVERBOUND_PLAY_KEEP_ALIVE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayKeepAlivePacketId());
	public static final PacketType SERVERBOUND_PLAY_CHAT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayChatPacketId());
	public static final PacketType SERVERBOUND_PLAY_USE_ENTITY = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUseEntityPacketId());
	public static final PacketType SERVERBOUND_PLAY_PLAYER = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPlayerPacketId());
	public static final PacketType SERVERBOUND_PLAY_POSITION = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPositionPacketId());
	public static final PacketType SERVERBOUND_PLAY_LOOK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayLookPacketId());
	public static final PacketType SERVERBOUND_PLAY_POSITION_LOOK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPositionLookPacketId());
	public static final PacketType SERVERBOUND_PLAY_BLOCK_DIG = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayBlockDigPacketId());
	public static final PacketType SERVERBOUND_PLAY_BLOCK_PLACE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayBlockPlacePacketId());
	public static final PacketType SERVERBOUND_PLAY_HELD_SLOT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayHeldSlotPacketId());
	public static final PacketType SERVERBOUND_PLAY_ANIMATION = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAnimationPacketId());
	public static final PacketType SERVERBOUND_PLAY_ENTITY_ACTION = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEntityActionPacketId());
	public static final PacketType SERVERBOUND_PLAY_MOVE_VEHICLE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayMoveVehiclePacketId());
	public static final PacketType SERVERBOUND_PLAY_STEER_BOAT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySteerBoatPacketId());
	public static final PacketType SERVERBOUND_PLAY_STEER_VEHICLE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySteerVehiclePacketId());
	public static final PacketType SERVERBOUND_PLAY_WINDOW_CLOSE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowClosePacketId());
	public static final PacketType SERVERBOUND_PLAY_WINDOW_CLICK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowClickPacketId());
	public static final PacketType SERVERBOUND_PLAY_WINDOW_TRANSACTION = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayWindowTransactionPacketId());
	public static final PacketType SERVERBOUND_PLAY_CREATIVE_SET_SLOT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCreativeSetSlotPacketId());
	public static final PacketType SERVERBOUND_PLAY_ENCHANT_SELECT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEnchantSelectPacketId());
	public static final PacketType SERVERBOUND_PLAY_UPDATE_SIGN = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateSignPacketId());
	public static final PacketType SERVERBOUND_PLAY_ABILITIES = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAbilitiesPacketId());
	public static final PacketType SERVERBOUND_PLAY_TAB_COMPLETE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayTabCompletePacketId());
	public static final PacketType SERVERBOUND_PLAY_SETTINGS = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySettingsPacketId());
	public static final PacketType SERVERBOUND_PLAY_CLIENT_COMMAND = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayClientCommandPacketId());
	public static final PacketType SERVERBOUND_PLAY_CUSTOM_PAYLOAD = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCustomPayloadPacketId());
	public static final PacketType SERVERBOUND_PLAY_USE_ITEM = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUseItemPacketId());
	public static final PacketType SERVERBOUND_PLAY_SPECTATE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySpectatePacketId());
	public static final PacketType SERVERBOUND_PLAY_RESOURCE_PACK_STATUS = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayResourcePackStatusPacketId());
	public static final PacketType SERVERBOUND_PLAY_TELEPORT_ACCEPT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayTeleportAcceptPacketId());
	public static final PacketType SERVERBOUND_PLAY_RECIPE_BOOK_DATA = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayRecipeBookDataPacketId());
	public static final PacketType SERVERBOUND_PLAY_CRAFT_RECIPE_REQUEST = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayCraftRecipeRequestPacketId());
	public static final PacketType SERVERBOUND_PLAY_ADVANCEMENT_TAB = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayAdvancementTabPacketId());
	public static final PacketType SERVERBOUND_PLAY_QUERY_BLOCK_NBT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayQueryBlockNBTPacketId());
	public static final PacketType SERVERBOUND_PLAY_QUERY_ENTITY_NBT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayQueryEntityNBTPacketId());
	public static final PacketType SERVERBOUND_PLAY_EDIT_BOOK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayEditBookPacketId());
	public static final PacketType SERVERBOUND_PLAY_PICK_ITEM = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayPickItemPacketId());
	public static final PacketType SERVERBOUND_PLAY_NAME_ITEM = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayNameItemPacketId());
	public static final PacketType SERVERBOUND_PLAY_SELECT_TRADE = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySelectTradePacketId());
	public static final PacketType SERVERBOUND_PLAY_SET_BEACON_EFFECT = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlaySetBeaconEffectPacketId());
	public static final PacketType SERVERBOUND_PLAY_UPDATE_COMMAND_BLOCK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandBlockPacketId());
	public static final PacketType SERVERBOUND_PLAY_UPDATE_COMMAND_MINECART = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateCommandMinecartPacketId());
	public static final PacketType SERVERBOUND_PLAY_UPDATE_STRUCTURE_BLOCK = new PacketType(Direction.SERVERBOUND, ServerPlatform.get().getPacketFactory().getInPlayUpdateStructureBlockPacketId());

	public static final PacketType CLIENTBOUND_LOGIN_DISCONNECT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginDisconnectPacketId());
	public static final PacketType CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginEncryptionBeginPacketId());
	public static final PacketType CLIENTBOUND_LOGIN_SUCCESS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginSuccessPacketId());
	public static final PacketType CLIENTBOUND_LOGIN_SET_COMPRESSION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginSetCompressionPacketId());
	public static final PacketType CLIENTBOUND_LOGIN_CUSTOM_PAYLOAD = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutLoginCustomPayloadPacketId());
	public static final PacketType CLIENTBOUND_STATUS_SERVER_INFO = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutStatusServerInfoPacketId());
	public static final PacketType CLIENTBOUND_STATUS_PONG = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutStatusPongPacketId());
	public static final PacketType CLIENTBOUND_PLAY_KEEP_ALIVE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayKeepAlivePacketId());
	public static final PacketType CLIENTBOUND_PLAY_START_GAME = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayLoginPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CHAT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChatPacketId());
	public static final PacketType CLIENTBOUND_PLAY_UPDATE_TIME = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateTimePacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_POSITION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnPositionPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SET_HEALTH = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateHealthPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SET_EXPERIENCE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayExperiencePacketId());
	public static final PacketType CLIENTBOUND_PLAY_SET_COOLDOWN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetCooldownPacketId());
	public static final PacketType CLIENTBOUND_PLAY_RESPAWN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayRespawnPacketId());
	public static final PacketType CLIENTBOUND_PLAY_POSITION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPositionPacketId());
	public static final PacketType CLIENTBOUND_PLAY_HELD_SLOT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayHeldSlotPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_NAMED = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnNamedPacketId());
	public static final PacketType CLIENTBOUND_PLAY_COLLECT_EFFECT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCollectEffectPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_OBJECT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnObjectPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_LIVING = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnLivingPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_PAINTING = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnPaintingPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_EXP_ORB = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnExpOrbPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SPAWN_GLOBAL = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySpawnWeatherPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_DESTROY = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityDestroyPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_VELOCITY = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityVelocityPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_NOOP = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_REL_MOVE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMovePacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_LOOK = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityLookPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityRelMoveLookPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_TELEPORT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityTeleportPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_HEAD_ROTATION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityHeadRotationPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_LEASH = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityLeashPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_PASSENGERS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetPassengersPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_METADATA = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityMetadataPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityAttributesPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_STATUS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityStatusPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_ANIMATION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAnimationPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectAddPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEffectRemovePacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_EQUIPMENT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntityEquipmentPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ENTITY_SOUND = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayEntitySoundPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CHUNK_SINGLE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkSinglePacketId());
	public static final PacketType CLIENTBOUND_PLAY_CHUNK_LIGHT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkLightPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeMultiPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockChangeSinglePacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_TILE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateTilePacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_ACTION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockActionPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBlockBreakAnimationPacketId());
	public static final PacketType CLIENTBOUND_PLAY_EXPLOSION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayExplosionPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WORLD_EVENT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldEventPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WORLD_SOUND = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldSoundPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WORLD_PARTICLES = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldParticlesPacketId());
	public static final PacketType CLIENTBOUND_PLAY_GAME_STATE_CHANGE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayGameStateChangePacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_OPEN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowOpenPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_HORSE_OPEN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowHorseOpenPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_CLOSE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowClosePacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_SET_SLOT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowSetSlotPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_SET_ITEMS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowSetItemsPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_DATA = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowDataPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WINDOW_TRANSACTION = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWindowTransactionPacketId());
	public static final PacketType CLIENTBOUND_PLAY_UPDATE_MAP = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayMapPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SIGN_EDITOR = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySignEditorPacketId());
	public static final PacketType CLIENTBOUND_PLAY_STATISTICS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayStatisticsPacketId());
	public static final PacketType CLIENTBOUND_PLAY_PLAYER_INFO = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPlayerInfoPacketId());
	public static final PacketType CLIENTBOUND_PLAY_PLAYER_ABILITIES = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAbilitiesPacketId());
	public static final PacketType CLIENTBOUND_PLAY_TAB_COMPLETE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayTabCompletePacketId());
	public static final PacketType CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardObjectivePacketId());
	public static final PacketType CLIENTBOUND_PLAY_SCOREBOARD_SCORE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardScorePacketId());
	public static final PacketType CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardDisplaySlotPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SCOREBOARD_TEAM = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayScoreboardTeamPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CUSTOM_PAYLOAD = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCustomPayloadPacketId());
	public static final PacketType CLIENTBOUND_PLAY_KICK_DISCONNECT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayKickDisconnectPacketId());
	public static final PacketType CLIENTBOUND_PLAY_RESOURCE_PACK = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayResourcePackPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CAMERA = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCameraPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WORLD_BORDER = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldBorderPacketId());
	public static final PacketType CLIENTBOUND_PLAY_TITLE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayTitlePacketId());
	public static final PacketType CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayPlayerListHeaderFooterPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CHUNK_UNLOAD = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayChunkUnloadPacketId());
	public static final PacketType CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayWorldCustomSoundPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SERVER_DIFFICULTY = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayServerDifficultyPacketId());
	public static final PacketType CLIENTBOUND_PLAY_COMBAT_EVENT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCombatEventPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BOSS_BAR = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBossBarPacketId());
	public static final PacketType CLIENTBOUND_PLAY_VEHICLE_MOVE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayVehicleMovePacketId());
	public static final PacketType CLIENTBOUND_PLAY_UNLOCK_RECIPES = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUnlockRecipesPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ADVANCEMENTS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsPacketId());
	public static final PacketType CLIENTBOUND_PLAY_ADVANCEMENTS_TAB = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAdvancementsTabPacketId());
	public static final PacketType CLIENTBOUND_PLAY_CRAFT_RECIPE_CONFIRM = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayCraftRecipeConfirmPacketId());
	public static final PacketType CLIENTBOUND_PLAY_DECLARE_COMMANDS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareCommandsPacketId());
	public static final PacketType CLIENTBOUND_PLAY_DECLARE_RECIPES = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareRecipesPacketId());
	public static final PacketType CLIENTBOUND_PLAY_DECLARE_TAGS = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayDeclareTagsPacket());
	public static final PacketType CLIENTBOUND_PLAY_QUERY_NBT_RESPONSE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayQueryNBTResponsePacketId());
	public static final PacketType CLIENTBOUND_PLAY_STOP_SOUND = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayStopSoundPacketId());
	public static final PacketType CLIENTBOUND_PLAY_LOOK_AT = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayLookAtPacketId());
	public static final PacketType CLIENTBOUND_PLAY_SET_VIEW_CENTER = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlaySetViewCenterPacketId());
	public static final PacketType CLIENTBOUND_PLAY_UPDATE_VIEW_DISTANCE = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayUpdateViewDistancePacketId());
	public static final PacketType CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayMerchantTradeListPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BOOK_OPEN = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayBookOpenPacketId());
	public static final PacketType CLIENTBOUND_PLAY_BLOCK_BREAK_CONFIRM = new PacketType(Direction.CLIENTBOUND, ServerPlatform.get().getPacketFactory().getOutPlayAcknowledgePlayerDiggingId());

	public static final PacketType CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID = new PacketType(Direction.CLIENTBOUND, -1);
	public static final PacketType CLIENTBOUND_LEGACY_PLAY_USE_BED_ID = new PacketType(Direction.CLIENTBOUND, -1);

	public static final int MAX_PACKET_ID_LENGTH = Math.max(VarNumberSerializer.MAX_LENGTH, Byte.BYTES);

	private final Direction direction;
	private final int id;
	private final int ordinal;
	protected PacketType(Direction direction, int id) {
		this.direction = direction;
		this.id = id;
		this.ordinal = ordinalCtr++;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getId() {
		return id;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public static enum Direction {
		CLIENTBOUND, SERVERBOUND, NONE
	}

}
