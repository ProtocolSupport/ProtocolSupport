package protocolsupport.protocol.types.networkentity.metadata;

import java.util.concurrent.atomic.AtomicInteger;

import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectCatVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFrogVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBT;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSnifferState;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarLong;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector4f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;

public class NetworkEntityMetadataObjectIndexRegistry {

	public static final NetworkEntityMetadataObjectIndexRegistry INSTANCE = new NetworkEntityMetadataObjectIndexRegistry();

	protected final AtomicInteger lastTakenId = new AtomicInteger(0);
	protected <T, TO extends NetworkEntityMetadataObject<T>> NetworkEntityMetadataObjectIndex<TO> takeNextIndex(Class<TO> expectedType) {
		return new NetworkEntityMetadataObjectIndex<>(getClass(), lastTakenId.getAndIncrement(), expectedType);
	}

	public static class EntityIndexRegistry extends NetworkEntityMetadataObjectIndexRegistry {

		public static final EntityIndexRegistry INSTANCE = new EntityIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> BASE_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> AIR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalChat> NAMETAG = takeNextIndex(NetworkEntityMetadataObjectOptionalChat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> NAMETAG_VISIBLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SILENT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> NO_GRAVITY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectEntityPose> POSE = takeNextIndex(NetworkEntityMetadataObjectEntityPose.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> FROZEN_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class EntityLivingIndexRegistry extends EntityIndexRegistry {

		public static final EntityLivingIndexRegistry INSTANCE = new EntityLivingIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HAND_USE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> HEALTH = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> POTION_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POTION_AMBIENT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ARROWS_IN = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ABSORBTION_HEALTH = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> BED_LOCATION = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
	}

	public static class InsentientIndexRegistry extends EntityLivingIndexRegistry {

		public static final InsentientIndexRegistry INSTANCE = new InsentientIndexRegistry();

		public static final int INS_FLAGS_BIT_ATTACKING = 2;

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> INS_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class PlayerIndexRegistry extends EntityLivingIndexRegistry {

		public static final PlayerIndexRegistry INSTANCE = new PlayerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> ADDITIONAL_HEARTS = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SCORE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SKIN_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> MAIN_HAND = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectNBT> LEFT_SHOULDER_ENTITY = takeNextIndex(NetworkEntityMetadataObjectNBT.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectNBT> RIGHT_SHOULDER_ENTITY = takeNextIndex(NetworkEntityMetadataObjectNBT.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> DEATH_LOCATION = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
	}

	public static class AgeableIndexRegistry extends InsentientIndexRegistry {

		public static final AgeableIndexRegistry INSTANCE = new AgeableIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IS_BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		//age - special hack for hologram plugins that want to set int age
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> AGE_HACK = new NetworkEntityMetadataObjectIndex<>(getClass(), 30, NetworkEntityMetadataObjectVarInt.class);
	}

	public static class RaidParticipantIndexRegistry extends InsentientIndexRegistry {

		public static final RaidParticipantIndexRegistry INSTANCE = new RaidParticipantIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CELEBRATING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class TameableIndexRegistry extends AgeableIndexRegistry {

		public static final TameableIndexRegistry INSTANCE = new TameableIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> TAME_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> OWNER = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
	}

	public static class ArmorStandIndexRegistry extends EntityLivingIndexRegistry {

		public static final ArmorStandIndexRegistry INSTANCE = new ArmorStandIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> ARMORSTAND_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> HEAD_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> BODY_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> LEFT_ARM_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> RIGHT_ARM_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> LEFT_LEG_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> RIGHT_LEG_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
	}

	public static class HorseIndexRegistry extends AgeableIndexRegistry {

		public static final HorseIndexRegistry INSTANCE = new HorseIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HORSE_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class BattleHorseIndexRegistry extends HorseIndexRegistry {

		public static final BattleHorseIndexRegistry INSTANCE = new BattleHorseIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class CargoHorseIndexRegistry extends HorseIndexRegistry {

		public static final CargoHorseIndexRegistry INSTANCE = new CargoHorseIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_CHEST = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class LamaIndexRegistry extends CargoHorseIndexRegistry {

		public static final LamaIndexRegistry INSTANCE = new LamaIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> STRENGTH = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> CARPET_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class CamelIndexRegistry extends HorseIndexRegistry {

		public static final CamelIndexRegistry INSTANCE = new CamelIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DASING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarLong> LAST_POSE_CHANGE_TIME = takeNextIndex(NetworkEntityMetadataObjectVarLong.class);
	}

	public static class BatIndexRegistry extends InsentientIndexRegistry {

		public static final BatIndexRegistry INSTANCE = new BatIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HANGING = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class WolfIndexRegistry extends TameableIndexRegistry {

		public static final WolfIndexRegistry INSTANCE = new WolfIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> BEGGING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLLAR_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class CatIndexRegistry extends TameableIndexRegistry {

		public static final CatIndexRegistry INSTANCE = new CatIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectCatVariant> VARIANT = takeNextIndex(NetworkEntityMetadataObjectCatVariant.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> LYING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> RELAXING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLLAR_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class OcelotIndexRegistry extends AgeableIndexRegistry {

		public static final OcelotIndexRegistry INSTANCE = new OcelotIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> TRUSTING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class PigIndexRegistry extends AgeableIndexRegistry {

		public static final PigIndexRegistry INSTANCE = new PigIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_SADLLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BOOST_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class MushroomCowIndexRegistry extends AgeableIndexRegistry {

		public static final MushroomCowIndexRegistry INSTANCE = new MushroomCowIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectString> VARIANT = takeNextIndex(NetworkEntityMetadataObjectString.class);
	}

	public static class RabbitIndexRegistry extends AgeableIndexRegistry {

		public static final RabbitIndexRegistry INSTANCE = new RabbitIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class SheepIndexRegistry extends AgeableIndexRegistry {

		public static final SheepIndexRegistry INSTANCE = new SheepIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SHEEP_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class PolarBearIndexRegistry extends AgeableIndexRegistry {

		public static final PolarBearIndexRegistry INSTANCE = new PolarBearIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> STANDING_UP = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class AbstractMerchantIndexRegistry extends AgeableIndexRegistry {

		public static final AbstractMerchantIndexRegistry INSTANCE = new AbstractMerchantIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> HEAD_SHAKE_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class VillagerIndexRegistry extends AbstractMerchantIndexRegistry {

		public static final VillagerIndexRegistry INSTANCE = new VillagerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> VDATA = takeNextIndex(NetworkEntityMetadataObjectVillagerData.class);
	}

	public static class FoxIndexRegistry extends AgeableIndexRegistry {

		public static final FoxIndexRegistry INSTANCE = new FoxIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FOX_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> UNKNOWN_1 = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> UNKNOWN_2 = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
	}

	public static class PandaIndexRegistry extends AgeableIndexRegistry {

		public static final PandaIndexRegistry INSTANCE = new PandaIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BREED_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SNEEZE_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> EAT_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> GENE_MAIN = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> GENE_HIDDEN = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> PANDA_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class TurtleIndexRegistry extends AgeableIndexRegistry {

		public static final TurtleIndexRegistry INSTANCE = new TurtleIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectPosition> HOME_POS = takeNextIndex(NetworkEntityMetadataObjectPosition.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_EGG = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> LAYING_EGG = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectPosition> TRAVEL_POS = takeNextIndex(NetworkEntityMetadataObjectPosition.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> GOING_HOME = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> TRAVELING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class BeeIndexRegistry extends AgeableIndexRegistry {

		public static final BeeIndexRegistry INSTANCE = new BeeIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> BEE_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ANGER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class AxolotlIndexRegistry extends AgeableIndexRegistry {

		public static final AxolotlIndexRegistry INSTANCE = new AxolotlIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> PLAYING_DEAD = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> FROM_BUCKET = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class GoatIndexRegistry extends AgeableIndexRegistry {

		public static final GoatIndexRegistry INSTANCE = new GoatIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SCREAMING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_LEFT_HORN = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_RIGHT_HORN = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class FrogIndexRegistry extends AgeableIndexRegistry {

		public static final FrogIndexRegistry INSTANCE = new FrogIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFrogVariant> VARIANT = takeNextIndex(NetworkEntityMetadataObjectFrogVariant.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalVarInt> TONGOUE_TARGET = takeNextIndex(NetworkEntityMetadataObjectOptionalVarInt.class);
	}

	public static class SnifferIndexRegistry extends AgeableIndexRegistry {

		public static final SnifferIndexRegistry INSTANCE = new SnifferIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectSnifferState> STATE = takeNextIndex(NetworkEntityMetadataObjectSnifferState.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> DROP_SEED_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class EndermanIndexRegistry extends InsentientIndexRegistry {

		public static final EndermanIndexRegistry INSTANCE = new EndermanIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> CARRIED_BLOCK = takeNextIndex(NetworkEntityMetadataObjectBlockData.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SCREAMING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> STARED_AT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class EnderDragonIndexRegistry extends InsentientIndexRegistry {

		public static final EnderDragonIndexRegistry INSTANCE = new EnderDragonIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> PHASE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class SnowmanIndexRegistry extends InsentientIndexRegistry {

		public static final SnowmanIndexRegistry INSTANCE = new SnowmanIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> NO_HAT = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class ZombieIndexRegistry extends InsentientIndexRegistry {

		public static final ZombieIndexRegistry INSTANCE = new ZombieIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> UNUSED = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DROWNING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ZombieVillagerIndexRegistry extends ZombieIndexRegistry {

		public static final ZombieVillagerIndexRegistry INSTANCE = new ZombieVillagerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CONVERTING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> VDATA = takeNextIndex(NetworkEntityMetadataObjectVillagerData.class);
	}

	public static class BlazeIndexRegistry extends InsentientIndexRegistry {

		public static final BlazeIndexRegistry INSTANCE = new BlazeIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> ON_FIRE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class SpiderIndexRegistry extends InsentientIndexRegistry {

		public static final SpiderIndexRegistry INSTANCE = new SpiderIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> CLIMBING = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class CreeperIndexRegistry extends InsentientIndexRegistry {

		public static final CreeperIndexRegistry INSTANCE = new CreeperIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> STATE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POWERED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IGNITED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class GhastIndexRegistry extends InsentientIndexRegistry {

		public static final GhastIndexRegistry INSTANCE = new GhastIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> ATTACKING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class SlimeIndexRegistry extends InsentientIndexRegistry {

		public static final SlimeIndexRegistry INSTANCE = new SlimeIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SIZE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class IronGolemIndexRegistry extends InsentientIndexRegistry {

		public static final IronGolemIndexRegistry INSTANCE = new IronGolemIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> PLAYER_CREATED = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class ShulkerIndexRegistry extends InsentientIndexRegistry {

		public static final ShulkerIndexRegistry INSTANCE = new ShulkerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectDirection> DIRECTION = takeNextIndex(NetworkEntityMetadataObjectDirection.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SHIELD_HEIGHT = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> COLOR = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class WitherIndexRegistry extends InsentientIndexRegistry {

		public static final WitherIndexRegistry INSTANCE = new WitherIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET1 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET2 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET3 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> INVULNERABLE_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class GuardianIndexRegistry extends InsentientIndexRegistry {

		public static final GuardianIndexRegistry INSTANCE = new GuardianIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SPIKES = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET_ID = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class VexIndexRegistry extends InsentientIndexRegistry {

		public static final VexIndexRegistry INSTANCE = new VexIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> ATTACK_MODE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class ParrotIndexRegistry extends TameableIndexRegistry {

		public static final ParrotIndexRegistry INSTANCE = new ParrotIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class PhantomIndexRegistry extends InsentientIndexRegistry {

		public static final PhantomIndexRegistry INSTANCE = new PhantomIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SIZE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class WardenIndexRegistry extends InsentientIndexRegistry {

		public static final WardenIndexRegistry INSTANCE = new WardenIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ANGER_LEVEL = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class AllayIndexRegistry extends InsentientIndexRegistry {

		public static final AllayIndexRegistry INSTANCE = new AllayIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DANCING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CAN_DUPLICATE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class BaseFishIndexRegistry extends InsentientIndexRegistry {

		public static final BaseFishIndexRegistry INSTANCE = new BaseFishIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> FROM_BUCKET = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class PufferFishIndexRegistry extends BaseFishIndexRegistry {

		public static final PufferFishIndexRegistry INSTANCE = new PufferFishIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> PUFF_STATE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class TropicalFishIndexRegistry extends BaseFishIndexRegistry {

		public static final TropicalFishIndexRegistry INSTANCE = new TropicalFishIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class WitchIndexRegistry extends RaidParticipantIndexRegistry {

		public static final WitchIndexRegistry INSTANCE = new WitchIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DRINKING_POTION = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class SpellcasterIllagerIndexRegistry extends RaidParticipantIndexRegistry {

		public static final SpellcasterIllagerIndexRegistry INSTANCE = new SpellcasterIllagerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SPELL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class PillagerIndexRegistry extends RaidParticipantIndexRegistry {

		public static final PillagerIndexRegistry INSTANCE = new PillagerIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> USING_CROSSBOW = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class BasePiglinIndexRegistry extends InsentientIndexRegistry {

		public static final BasePiglinIndexRegistry INSTANCE = new BasePiglinIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> ZOMBIFICATION_IMMUNITY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class PiglinIndexRegistry extends BasePiglinIndexRegistry {

		public static final PiglinIndexRegistry INSTANCE = new PiglinIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IS_BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> USING_CROSSBOW = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DANDCING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class HoglinIndexRegistry extends AgeableIndexRegistry {

		public static final HoglinIndexRegistry INSTANCE = new HoglinIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> ZOMBIFICATION_IMMUNITY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ZoglinIndexRegistry extends InsentientIndexRegistry {

		public static final ZoglinIndexRegistry INSTANCE = new ZoglinIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IS_BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class StriderIndexRegistry extends AgeableIndexRegistry {

		public static final StriderIndexRegistry INSTANCE = new StriderIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BOOST_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DISPLAY_NAMETAG = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_SADDLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class BoatIndexRegistry extends EntityIndexRegistry {

		public static final BoatIndexRegistry INSTANCE = new BoatIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TIME_SINCE_LAST_HIT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> FORWARD_DIRECTION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> DAMAGE_TAKEN = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> LEFT_PADDLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> RIGHT_PADDLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SPLASH_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class PaintingIndexRegistry extends EntityIndexRegistry {

		public static final PaintingIndexRegistry INSTANCE = new PaintingIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class TntIndexRegistry extends EntityIndexRegistry {

		public static final TntIndexRegistry INSTANCE = new TntIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> FUSE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class WitherSkullIndexRegistry extends EntityIndexRegistry {

		public static final WitherSkullIndexRegistry INSTANCE = new WitherSkullIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CHARGED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class PotionIndexRegistry extends EntityIndexRegistry {

		public static final PotionIndexRegistry INSTANCE = new PotionIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
	}

	public static class FishingFloatIndexRegistry extends EntityIndexRegistry {

		public static final FishingFloatIndexRegistry INSTANCE = new FishingFloatIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> HOOKED_ENTITY = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CATCHABLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ItemIndexRegistry extends EntityIndexRegistry {

		public static final ItemIndexRegistry INSTANCE = new ItemIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
	}

	public static class MinecartIndexRegistry extends EntityIndexRegistry {

		public static final MinecartIndexRegistry INSTANCE = new MinecartIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SHAKING_POWER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SHAKING_DIRECTION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> DAMAGE_TAKEN = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BLOCK = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BLOCK_Y = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOW_BLOCK = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class MinecartFurnaceIndexRegistry extends MinecartIndexRegistry {

		public static final MinecartFurnaceIndexRegistry INSTANCE = new MinecartFurnaceIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POWERED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class MinecartCommandIndexRegistry extends MinecartIndexRegistry {

		public static final MinecartCommandIndexRegistry INSTANCE = new MinecartCommandIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectString> COMMAND = takeNextIndex(NetworkEntityMetadataObjectString.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectChat> LAST_OUTPUT = takeNextIndex(NetworkEntityMetadataObjectChat.class);
	}

	public static class ArrowIndexRegistry extends EntityIndexRegistry {

		public static final ArrowIndexRegistry INSTANCE = new ArrowIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> CRITICAL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> PIERCING_LEVEL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class TippedArrowIndexRegistry extends ArrowIndexRegistry {

		public static final TippedArrowIndexRegistry INSTANCE = new TippedArrowIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class TridentIndexRegistry extends ArrowIndexRegistry {

		public static final TridentIndexRegistry INSTANCE = new TridentIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> LOYALTY = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_GLINT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class FireworkIndexRegistry extends EntityIndexRegistry {

		public static final FireworkIndexRegistry INSTANCE = new FireworkIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalVarInt> USER = takeNextIndex(NetworkEntityMetadataObjectOptionalVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOT_AT_ANGLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ItemFrameIndexRegistry extends EntityIndexRegistry {

		public static final ItemFrameIndexRegistry INSTANCE = new ItemFrameIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ROTATION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class EnderCrystalIndexRegistry extends EntityIndexRegistry {

		public static final EnderCrystalIndexRegistry INSTANCE = new EnderCrystalIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> TARGET = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOW_BOTTOM = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class AreaEffectCloudIndexRegistry extends EntityIndexRegistry {

		public static final AreaEffectCloudIndexRegistry INSTANCE = new AreaEffectCloudIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> RADIUS = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SINGLE_POINT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> PARTICLE = takeNextIndex(NetworkEntityMetadataObjectParticle.class);
	}

	public static class InteractionIndexRegistry extends EntityIndexRegistry {

		public static final InteractionIndexRegistry INSTANCE = new InteractionIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> WIDTH = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> HEIGHT = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> INTERACTABLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class DisplayIndexRegistry extends EntityIndexRegistry {

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> INTERP_DEPLAY = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> INTERP_DURATION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> TRANSLATION = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> SCALE = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector4f> ROTATION_LEFT = takeNextIndex(NetworkEntityMetadataObjectVector4f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector4f> ROTATION_RIGHT = takeNextIndex(NetworkEntityMetadataObjectVector4f.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> CONSTRAINTS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BRIGHTNESS = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> VIEW_RANGE = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> SHADOW_RADIUS = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> SHADOW_STRENGTH = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> WIDTH = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> HEIGHT = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> GLOW_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class DisplayBlockIndexRegistry extends DisplayIndexRegistry {

		public static final DisplayBlockIndexRegistry INSTANCE = new DisplayBlockIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> BLOCK = takeNextIndex(NetworkEntityMetadataObjectBlockData.class);
	}

	public static class DisplayItemIndexRegistry extends DisplayIndexRegistry {

		public static final DisplayItemIndexRegistry INSTANCE = new DisplayItemIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> TYPE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class DisplayTextIndexRegistry extends DisplayIndexRegistry {

		public static final DisplayTextIndexRegistry INSTANCE = new DisplayTextIndexRegistry();

		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectChat> TEXT = takeNextIndex(NetworkEntityMetadataObjectChat.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> WIDTH = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> OPACITY = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

}
