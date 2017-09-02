package protocolsupport.zplatform;

import java.security.PublicKey;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.protocol.utils.types.Position;

public interface PlatformPacketFactory {

	public Object createInboundInventoryClosePacket();
	
	public Object createInboundInventoryConfirmTransactionPacket(int windowId, int actionNumber, boolean accepted);

	public Object createOutboundChatPacket(String message, int position);

	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer);

	public Object createTitleResetPacket();

	public Object createTitleClearPacket();

	public Object createTitleMainPacket(String title);

	public Object createTitleSubPacket(String title);

	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut);

	public Object createLoginDisconnectPacket(String message);

	public Object createPlayDisconnectPacket(String message);

	public Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes);

	public Object createSetCompressionPacket(int threshold);

	public Object createBlockBreakSoundPacket(Position pos, Material type);

	public Object createStatusPongPacket(long pingId);

	public Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers);

	public Object createLoginSuccessPacket(GameProfile profile);

	public Object createEmptyCustomPayloadPacket(String tag);

	public Object createFakeJoinGamePacket();

	public Object createEntityStatusPacket(Entity entity, int status);


	public int getOutLoginDisconnectPacketId();

	public int getOutLoginEncryptionBeginPacketId();

	public int getOutLoginSuccessPacketId();

	public int getOutLoginSetCompressionPacketId();

	public int getOutStatusServerInfoPacketId();

	public int getOutStatusPongPacketId();

	public int getOutPlayKeepAlivePacketId();

	public int getOutPlayLoginPacketId();

	public int getOutPlayChatPacketId();

	public int getOutPlayUpdateTimePacketId();

	public int getOutPlayEntityEquipmentPacketId();

	public int getOutPlaySpawnPositionPacketId();

	public int getOutPlayUpdateHealthPacketId();

	public int getOutPlayRespawnPacketId();

	public int getOutPlayPositionPacketId();

	public int getOutPlayHeldSlotPacketId();

	public int getOutPlayBedPacketId();

	public int getOutPlayAnimationPacketId();

	public int getOutPlaySpawnNamedPacketId();

	public int getOutPlayCollectEffectPacketId();

	public int getOutPlaySpawnObjectPacketId();

	public int getOutPlaySpawnLivingPacketId();

	public int getOutPlaySpawnPaintingPacketId();

	public int getOutPlaySpawnExpOrbPacketId();

	public int getOutPlayEntityVelocityPacketId();

	public int getOutPlayEntityDestroyPacketId();

	public int getOutPlayEntityPacketId();

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

	public int getOutPlayExperiencePacketId();

	public int getOutPlayEntityAttributesPacketId();

	public int getOutPlayChunkSinglePacketId();

	public int getOutPlayBlockChangeMultiPacketId();

	public int getOutPlayBlockChangeSinglePacketId();

	public int getOutPlayBlockActionPacketId();

	public int getOutPlayBlockBreakAnimationPacketId();

	public int getOutPlayExplosionPacketId();

	public int getOutPlayWorldEventPacketId();

	public int getOutPlayWorldSoundPacketId();

	public int getOutPlayWorldParticlesPacketId();

	public int getOutPlayGameStateChangePacketId();

	public int getOutPlaySpawnWeatherPacketId();

	public int getOutPlayWindowOpenPacketId();

	public int getOutPlayWindowClosePacketId();

	public int getOutPlayWindowSetSlotPacketId();

	public int getOutPlayWindowSetItemsPacketId();

	public int getOutPlayWindowDataPacketId();

	public int getOutPlayWindowTransactionPacketId();

	public int getOutPlayMapPacketId();

	public int getOutPlayUpdateTilePacketId();

	public int getOutPlaySignEditorPacketId();

	public int getOutPlayStatisticsPacketId();

	public int getOutPlayPlayerInfoPacketId();

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

	public int getOutPlayWorldBorderPacketId();

	public int getOutPlayTitlePacketId();

	public int getOutPlayPlayerListHeaderFooterPacketId();

	public int getOutPlaySetPassengersPacketId();

	public int getOutPlayChunkUnloadPacketId();

	public int getOutPlayWorldCustomSoundPacketId();

	public int getOutPlayServerDifficultyPacketId();

	public int getOutPlayCombatEventPacketId();

	public int getOutPlayBossBarPacketId();

	public int getOutPlaySetCooldownPacketId();

	public int getOutPlayVehicleMovePacketId();

	public int getOutPlayUnlockRecipesPacketId();

	public int getOutPlayAdvancementsPacketId();

	public int getOutPlayAdvancementsTabPacketId();

	public int getOutPlayCraftingGridConfirmPacketId();


	public int getInHandshakeStartPacketId();

	public int getInStatusRequestPacketId();

	public int getInStatusPingPacketId();

	public int getInLoginStartPacketId();

	public int getInLoginEncryptionBeginPacketId();

	public int getInPlayKeepAlivePacketId();

	public int getInPlayChatPacketId();

	public int getInPlayUseEntityPacketId();

	public int getInPlayPlayerPacketId();

	public int getInPlayPositionPacketId();

	public int getInPlayLookPacketId();

	public int getInPlayPositionLookPacketId();

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

	public int getInPlayWindowTransactionPacketId();

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

	public int getInPlayCraftingBookPacketId();

	public int getInPlayPrepareCraftingGridPacketId();

	public int getInPlayAdvancementTabPacketId();

}
