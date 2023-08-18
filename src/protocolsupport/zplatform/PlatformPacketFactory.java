package protocolsupport.zplatform;

import java.util.List;

import org.bukkit.entity.Entity;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.api.utils.Profile;

public interface PlatformPacketFactory {

	public Object createOutboundSystemChatPacket(String message, boolean overlay);

	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer);

	public Object createTitleResetPacket();

	public Object createTitleClearPacket();

	public Object createTitleMainPacket(String title);

	public Object createTitleSubPacket(String title);

	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut);

	public Object createLoginDisconnectPacket(BaseComponent message);

	public Object createPlayDisconnectPacket(BaseComponent message);

	public Object createLoginEncryptionBeginPacket(byte[] publicKey, byte[] randomBytes);

	public Object createSetCompressionPacket(int threshold);

	public Object createStatusPongPacket(long pingId);

	public Object createStatusServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, BaseComponent motd, int onlinePlayers, int maxPlayers);

	public Object createLoginSuccessPacket(Profile profile);

	public Object createEmptyCustomPayloadPacket(String tag);

	public Object createFakeJoinGamePacket();

	public Object createEntityStatusPacket(Entity entity, int status);


	public int getOutLoginDisconnectPacketId();

	public int getOutLoginEncryptionBeginPacketId();

	public int getOutLoginSuccessPacketId();

	public int getOutLoginSetCompressionPacketId();

	public int getOutLoginCustomPayloadPacketId();

	public int getOutStatusServerInfoPacketId();

	public int getOutStatusPongPacketId();

	public int getOutPlayBundleSeparatorPacketId();

	public int getOutPlayServerDataPacketId();

	public int getOutPlayGameFeaturesPacketId();

	public int getOutPlayKeepAlivePacketId();

	public int getOutPlayLoginPacketId();

	public int getOutPlayPlayerMessagePacketId();

	public int getOutPlayPlayerMessageDeletePacketId();

	public int getOutPlaySystemMessagePacketId();

	public int getOutPlaySystemPlayerMessagePacketId();

	public int getOutPlayUpdateTimePacketId();

	public int getOutPlayEntityEquipmentPacketId();

	public int getOutPlaySpawnPositionPacketId();

	public int getOutPlayUpdateHealthPacketId();

	public int getOutPlayRespawnPacketId();

	public int getOutPlayPositionPacketId();

	public int getOutPlayHeldSlotPacketId();

	public int getOutPlayAnimationPacketId();

	public int getOutPlaySpawnNamedPacketId();

	public int getOutPlayCollectEffectPacketId();

	public int getOutPlaySpawnEntityPacketId();

	public int getOutPlaySpawnExpOrbPacketId();

	public int getOutPlayEntityVelocityPacketId();

	public int getOutPlayEntityDestroyPacketId();

	public int getOutPlayEntityRelMovePacketId();

	public int getOutPlayEntityLookPacketId();

	public int getOutPlayEntityRelMoveLookPacketId();

	public int getOutPlayEntityTeleportPacketId();

	public int getOutPlayEntityHeadRotationPacketId();

	public int getOutPlayEntityStatusPacketId();

	public int getOutPlayEntityLeashPacketId();

	public int getOutPlayEntityMetadataPacketId();

	public int getOutPlayEntityEffectAddPacketId();

	public int getOutPlayEntityEffectRemovePacketId();

	public int getOutPlayEntitySoundPacketId();

	public int getOutPlayEntityDamageEventPacketId();

	public int getOutPlayEntityHurtAnimationPacketId();

	public int getOutPlayExperiencePacketId();

	public int getOutPlayEntityAttributesPacketId();

	public int getOutPlayChunkDataPacketId();

	public int getOutPlayChunkLightPacketId();

	public int getOutPlayChunkUnloadPacketId();

	public int getOutPlayBlockChangeMultiPacketId();

	public int getOutPlayBlockChangeSinglePacketId();

	public int getOutPlayBlockActionPacketId();

	public int getOutPlayBlockBreakAnimationPacketId();

	public int getOutPlayBlockChangeAckPacketId();

	public int getOutPlayExplosionPacketId();

	public int getOutPlayWorldEventPacketId();

	public int getOutPlayWorldSoundPacketId();

	public int getOutPlayWorldParticlesPacketId();

	public int getOutPlayGameStateChangePacketId();

	public int getOutPlayWindowOpenPacketId();

	public int getOutPlayWindowHorseOpenPacketId();

	public int getOutPlayWindowClosePacketId();

	public int getOutPlayWindowSetSlotPacketId();

	public int getOutPlayWindowSetItemsPacketId();

	public int getOutPlayWindowDataPacketId();

	public int getOutPlayMapPacketId();

	public int getOutPlayUpdateTilePacketId();

	public int getOutPlaySignEditorPacketId();

	public int getOutPlayStatisticsPacketId();

	public int getOutPlayPlayerInfoUpdatePacketId();

	public int getOutPlayPlayerInfoRemovePacketId();

	public int getOutPlayAbilitiesPacketId();

	public int getOutPlayTabCompletePacketId();

	public int getOutPlayScoreboardObjectivePacketId();

	public int getOutPlayScoreboardScorePacketId();

	public int getOutPlayScoreboardDisplaySlotPacketId();

	public int getOutPlayScoreboardTeamPacketId();

	public int getOutPlayCustomPayloadPacketId();

	public int getOutPlayKickDisconnectPacketId();

	public int getOutPlayResourcePackPacketId();

	public int getOutPlayCameraPacketId();

	public int getOutPlayPlayerListHeaderFooterPacketId();

	public int getOutPlaySetPassengersPacketId();

	public int getOutPlayServerDifficultyPacketId();

	public int getOutPlayBossBarPacketId();

	public int getOutPlaySetCooldownPacketId();

	public int getOutPlayVehicleMovePacketId();

	public int getOutPlayUnlockRecipesPacketId();

	public int getOutPlayAdvancementsPacketId();

	public int getOutPlayAdvancementsTabPacketId();

	public int getOutPlayCraftRecipeConfirmPacketId();

	public int getOutPlayDeclareCommandsPacketId();

	public int getOutPlayDeclareRecipesPacketId();

	public int getOutPlayDeclareTagsPacket();

	public int getOutPlayQueryNBTResponsePacketId();

	public int getOutPlayStopSoundPacketId();

	public int getOutPlayLookAtPacketId();

	public int getOutPlaySetViewCenterPacketId();

	public int getOutPlayUpdateViewDistancePacketId();

	public int getOutPlayUpdateSimulationDistancePacketId();

	public int getOutPlayMerchantTradeListPacketId();

	public int getOutPlayBookOpenPacketId();

	public int getOutPlayTitleTextPacketId();

	public int getOutPlayTitleSubTextPacketId();

	public int getOutPlayTitleAnimationPacketId();

	public int getOutPlayTitleClearPacketId();

	public int getOutPlayActionbarPacketId();

	public int getOutPlayWorldborderInitPacketId();

	public int getOutPlayWorldborderCenterPacketId();

	public int getOutPlayWorldborderLerpSizePacketId();

	public int getOutPlayWorldborderSizePacketId();

	public int getOutPlayWorldborderWarnDelayPacketId();

	public int getOutPlayWorldborderWarnDistancePacketId();

	public int getOutPlayCombatBeginPacketId();

	public int getOutPlayCombatEndPacketId();

	public int getOutPlayCombatDeathPacketId();

	public int getOutPlaySyncPing();

	public int getInHandshakeStartPacketId();

	public int getInStatusRequestPacketId();

	public int getInStatusPingPacketId();

	public int getInLoginStartPacketId();

	public int getInLoginEncryptionBeginPacketId();

	public int getInLoginCustomPayloadPacketId();

	public int getInPlayKeepAlivePacketId();

	public int getInPlayChatMessageAckPacketId();

	public int getInPlayChatSessionPacketId();

	public int getInPlayChatCommandPacketId();

	public int getInPlayChatMessagePacketId();

	public int getInPlayUseEntityPacketId();

	public int getInPlayPositionPacketId();

	public int getInPlayLookPacketId();

	public int getInPlayPositionLookPacketId();

	public int getInPlayGroundPacketId();

	public int getInPlayBlockDigPacketId();

	public int getInPlayBlockPlacePacketId();

	public int getInPlayHeldSlotPacketId();

	public int getInPlayAnimationPacketId();

	public int getInPlayEntityActionPacketId();

	public int getInPlayMoveVehiclePacketId();

	public int getInPlaySteerBoatPacketId();

	public int getInPlaySteerVehiclePacketId();

	public int getInPlayWindowClosePacketId();

	public int getInPlayWindowClickPacketId();

	public int getInPlayCreativeSetSlotPacketId();

	public int getInPlayEnchantSelectPacketId();

	public int getInPlayUpdateSignPacketId();

	public int getInPlayAbilitiesPacketId();

	public int getInPlayTabCompletePacketId();

	public int getInPlaySettingsPacketId();

	public int getInPlayClientCommandPacketId();

	public int getInPlayCustomPayloadPacketId();

	public int getInPlayUseItemPacketId();

	public int getInPlaySpectatePacketId();

	public int getInPlayResourcePackStatusPacketId();

	public int getInPlayTeleportAcceptPacketId();

	public int getInPlayRecipeBookRecipePacketId();

	public int getInPlayRecipeBookStatePacketId();

	public int getInPlayCraftRecipeRequestPacketId();

	public int getInPlayAdvancementTabPacketId();

	public int getInPlayQueryBlockNBTPacketId();

	public int getInPlayQueryEntityNBTPacketId();

	public int getInPlayEditBookPacketId();

	public int getInPlayPickItemPacketId();

	public int getInPlayNameItemPacketId();

	public int getInPlaySelectTradePacketId();

	public int getInPlaySetBeaconEffectPacketId();

	public int getInPlayUpdateCommandBlockPacketId();

	public int getInPlayUpdateCommandMinecartPacketId();

	public int getInPlayUpdateStructureBlockPacketId();

	public int getInPlayJigsawUpdatePacketId();

	public int getInPlayJigsawGenerateStructurePacketId();

	public int getInPlaySyncPong();

}
