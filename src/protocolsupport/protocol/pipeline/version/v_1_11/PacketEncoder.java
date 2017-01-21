package protocolsupport.protocol.pipeline.version.v_1_11;

import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.EncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_8__1_9_r1__1_9_r2__1_10__1_11.SetCompression;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_10__1_11.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_10__1_11.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_11.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_11.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_11.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.InventoryData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.ScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_110.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Animation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Explosion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.GameStateChange;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Respawn;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Statistics;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.TabComplete;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockAction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Camera;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.CombatEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Entity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.Login;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.PlayerInfo;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.PlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.ResourcePack;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.ScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.ScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.ServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.UseBed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.WorldBorder;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.BossBar;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityLeash;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityRelMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.EntityTeleport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.Map;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.Position;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.ScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SetCooldown;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SetPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.UnloadChunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11.VehicleMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r2__1_10__1_11.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.Pong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11.ServerInfo;
import protocolsupport.protocol.pipeline.version.AbstractPacketEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.zplatform.network.NetworkState;

public class PacketEncoder extends AbstractPacketEncoder {

	private static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		//TODO: remapping table with ClientBoundPacket ids
		for (NetworkState protocol : NetworkState.values()) {
			for (int i = 0; i < 255; i++) {
				packetIdRegistry.register(protocol, i, i);
			}
		}
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return packetIdRegistry.getNewPacketId(currentProtocol, oldPacketId);
	}

	{
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess.class);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, EncryptionRequest.class);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SET_COMPRESSION_ID, SetCompression.class);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_PONG_ID, Pong.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, Respawn.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, Position.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BED_ID, UseBed.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, Animation.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, Entity.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelMove.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityLook.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMoveLook.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LEASH_ID, EntityLeash.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND, WorldCustomSound.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, GameStateChange.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, BlockOpenSignEditor.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_STATISTICS, Statistics.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, TabComplete.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, ScoreboardObjective.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, ScoreboardScore.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, ScoreboardDisplay.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, ScoreboardTeam.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESOURCE_PACK_ID, ResourcePack.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CAMERA_ID, Camera.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER, PlayerListHeaderFooter.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_PASSENGERS, SetPassengers.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TITLE, Title.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_BORDER_ID, WorldBorder.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_UNLOAD, UnloadChunk.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SERVER_DIFFICULTY, ServerDifficulty.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COMBAT_EVENT, CombatEvent.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_COOLDOWN, SetCooldown.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BOSS_BAR, BossBar.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_VEHICLE_MOVE, VehicleMove.class);
	}

	public PacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

}
