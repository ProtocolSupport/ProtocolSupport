package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntitySetAttributes.AttributeInfo;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.watchedentity.DataWatcherRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.ObjectFloatTuple;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getAttributesCache().getLocale();
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return packets;
		}
		switch (entity.getType()) {
			case ITEM: {
				DataWatcherObjectIndex.Item.ITEM.getValue(metadata.getOriginal()).ifPresent(itemWatcher -> {
					NetworkEntityItemDataCache itemDataCache = (NetworkEntityItemDataCache) entity.getDataCache();
					packets.addAll(itemDataCache.updateItem(version, entity.getId(), itemWatcher.getValue()));
				});
				break;
			}
			default: {
				if (entity.getType().isOfType(NetworkEntityType.LIVING)) {
					DataWatcherObjectIndex.EntityLiving.HEALTH.getValue(metadata.getOriginal()).ifPresent(healthWatcher -> {
						packets.add(EntitySetAttributes.create(version, entity, new ObjectFloatTuple<>(AttributeInfo.HEALTH, healthWatcher.getValue())));
					});
				}
				if (entity.getType().isOfType(NetworkEntityType.BATTLE_HORSE)) {
					DataWatcherObjectIndex.BattleHorse.ARMOR.getValue(metadata.getOriginal()).ifPresent(armorWatcher -> {
						//int type = armorWatcher.getValue();
						packets.add(EntityEquipment.create(version, locale, entityId,
							NetworkItemStack.NULL,
							//TODO Fix for 1.13
							/*type == 0 ?*/ NetworkItemStack.NULL /*: ServerPlatform.get().getWrapperFactory().createItemStack(416 + armorWatcher.getValue())*/,
							NetworkItemStack.NULL,
							NetworkItemStack.NULL
						));
					});
				}
				packets.add(create(entity, locale, metadata.getRemapped(), version));
			}
		}
		return packets;
	}

	public static ClientBoundPacketData createFaux(NetworkEntity entity, String locale, ArrayMap<DataWatcherObject<?>> fauxMeta, ProtocolVersion version) {
		return create(entity, locale, transform(entity, fauxMeta, version), version);
	}

	public static ClientBoundPacketData createFaux(NetworkEntity entity, String locale, ProtocolVersion version) {
		DataWatcherRemapper faux = new DataWatcherRemapper();
		faux.remap(version, entity);
		return create(entity, locale, transform(entity, faux.getRemapped(), version), version);
	}

	public static ArrayMap<DataWatcherObject<?>> transform(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> peMetadata, ProtocolVersion version) {
		peMetadata.put(0, new DataWatcherObjectSVarLong(entity.getDataCache().getPeBaseFlags()));
		return peMetadata;
	}

	public static ClientBoundPacketData create(NetworkEntity entity, String locale, ArrayMap<DataWatcherObject<?>> peMetadata, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		DataWatcherDeserializer.encodePEData(serializer, version, locale, transform(entity, peMetadata, version));
		return serializer;
	}

	public static class PeMetaBase {

		//PE's extra baseflags. TODO: Implement more flags (Easy Remapping)
		protected static int flagId = 1;
		protected static int takeNextFlag() {
			return flagId++;
		}

		public static final int FLAG_ON_FIRE = takeNextFlag(); //0 (BitPos 1, flag 0)
		public static final int FLAG_SNEAKING = takeNextFlag(); //1
		public static final int FLAG_RIDING = takeNextFlag();
		public static final int FLAG_SPRINTING = takeNextFlag();
		public static final int FLAG_USING_ITEM = takeNextFlag();
		public static final int FLAG_INVISIBLE = takeNextFlag();
		public static final int FLAG_TEMPTED = takeNextFlag();
		public static final int FLAG_IN_LOVE = takeNextFlag();
		public static final int FLAG_SADDLED = takeNextFlag();
		public static final int FLAG_POWERED = takeNextFlag();
		public static final int FLAG_IGNITED = takeNextFlag(); //10
		public static final int FLAG_BABY = takeNextFlag();
		public static final int FLAG_CONVERTING = takeNextFlag();
		public static final int FLAG_CRITICAL = takeNextFlag();
		public static final int FLAG_SHOW_NAMETAG = takeNextFlag();
		public static final int FLAG_ALWAYS_SHOW_NAMETAG = takeNextFlag();
		public static final int FLAG_NO_AI = takeNextFlag();
		public static final int FLAG_SILENT = takeNextFlag();
		public static final int FLAG_CLIMBING = takeNextFlag();
		public static final int FLAG_CAN_CLIMB = takeNextFlag();
		public static final int FLAG_CAN_SWIM = takeNextFlag(); //20
		public static final int FLAG_CAN_FLY = takeNextFlag();
		public static final int FLAG_WALKER = takeNextFlag();
		public static final int FLAG_RESTING = takeNextFlag();
		public static final int FLAG_SITTING = takeNextFlag();
		public static final int FLAG_ANGRY = takeNextFlag();
		public static final int FLAG_INTERESTED = takeNextFlag();
		public static final int FLAG_CHARGED = takeNextFlag();
		public static final int FLAG_TAMED = takeNextFlag();
		public static final int FLAG_LEASHED = takeNextFlag();
		public static final int FLAG_SHEARED = takeNextFlag(); //30
		public static final int FLAG_GLIDING = takeNextFlag();
		public static final int FLAG_ELDER = takeNextFlag();
		public static final int FLAG_MOVING = takeNextFlag();
		public static final int FLAG_BREATHING = takeNextFlag();
		public static final int FLAG_CHESTED = takeNextFlag();
		public static final int FLAG_STACKABLE = takeNextFlag();
		public static final int FLAG_SHOW_BASE = takeNextFlag();
		public static final int FLAG_REARING = takeNextFlag();
		public static final int FLAG_VIBRATING = takeNextFlag();
		public static final int FLAG_IDLING = takeNextFlag(); //40
		public static final int FLAG_EVOKER_SPELL = takeNextFlag();
		public static final int FLAG_CHARGE_ATTACK = takeNextFlag();
		public static final int FLAG_WASD_CONTROLLED = takeNextFlag();
		public static final int FLAG_CAN_POWER_JUMP = takeNextFlag();
		public static final int FLAG_LINGER = takeNextFlag();
		public static final int FLAG_COLLIDE = takeNextFlag();
		public static final int FLAG_GRAVITY = takeNextFlag();
		public static final int FLAG_FIRE_IMMUNE = takeNextFlag();
		public static final int FLAG_DANCING = takeNextFlag();
		public static final int FLAG_ENCHANTED = takeNextFlag(); //50
		public static final int FLAG_SHOW_TRIDENT_ROPE = takeNextFlag();
		public static final int FLAG_CONTAINER_PRIVATE = takeNextFlag();
		public static final int FLAG_TRANSORMATION = takeNextFlag();
		public static final int FLAG_SPIN_ATTACK = takeNextFlag();
		public static final int FLAG_SWIMMING = takeNextFlag();
		public static final int FLAG_BRIBED = takeNextFlag();

		protected static int metaId = 0;
		protected static int takeNextMeta() {
			return metaId++;
		}

		public static final int FLAGS = takeNextMeta(); //0
		public static final int HEALTH = takeNextMeta(); //1
		public static final int VARIANT = takeNextMeta();
		public static final int COLOR = takeNextMeta();
		public static final int NAMETAG = takeNextMeta();
		public static final int OWNER = takeNextMeta();
		public static final int TARGET = takeNextMeta();
		public static final int AIR = takeNextMeta();
		public static final int POTION_COLOR = takeNextMeta();
		public static final int POTION_AMBIENT = takeNextMeta();
		public static final int UNKNOWN_1 = takeNextMeta(); //10
		public static final int HURT_TIME = takeNextMeta();
		public static final int HURT_DIRECTION = takeNextMeta();
		public static final int PADDLE_TIME_LEFT = takeNextMeta();
		public static final int PADDLE_TIME_RIGHT = takeNextMeta();
		public static final int XP_VALUE = takeNextMeta();
		public static final int MINECART_BLOCK = takeNextMeta(), EATING_HAYSTACK = 16, FIREWORK_TYPE = 16;
		public static final int MINECART_OFFSET = takeNextMeta();
		public static final int MINECART_DISPLAY = takeNextMeta();
		public static final int UNKNOWN_2 = takeNextMeta();
		public static final int UNKNOWN_3 = takeNextMeta(); //20
		public static final int UNKNOWN_4 = takeNextMeta();
		public static final int UNKNOWN_5 = takeNextMeta();
		public static final int ENDERMAN_BLOCK = takeNextMeta();
		public static final int AGE = takeNextMeta();
		public static final int UNKNOWN_6 = takeNextMeta();
		public static final int PLAYER_FLAGS = takeNextMeta();
		public static final int PLAYER_INDEX = takeNextMeta();
		public static final int COMPASS_LOCATION = takeNextMeta();
		public static final int FIREBALL_X = takeNextMeta();
		public static final int FIREBALL_Y = takeNextMeta(); //30
		public static final int FIREBALL_Z = takeNextMeta();
		public static final int UNKNOWN_7 = takeNextMeta();
		public static final int FISHINGFLOAT_X = takeNextMeta();
		public static final int FISHINGFLOAT_Y = takeNextMeta();
		public static final int FISHINGFLOAT_Z = takeNextMeta();
		public static final int POTION_AUX = takeNextMeta();
		public static final int LEADHOLDER = takeNextMeta();
		public static final int SCALE = takeNextMeta();
		public static final int BUTTON_TEXT = takeNextMeta();
		public static final int NPC_SKIN = takeNextMeta(); //40
		public static final int BUTTON_URL = takeNextMeta();
		public static final int MAX_AIR = takeNextMeta();
		public static final int MARK_VARIANT = takeNextMeta();
		public static final int HORSE_CONTAINER_TYPE = takeNextMeta();
		public static final int HORSE_ANIMAL_SLOTS = takeNextMeta();
		public static final int HORSE_CONTAINER_MULTIPLIER = takeNextMeta();
		public static final int BLOCK_TARGET = takeNextMeta();
		public static final int INVULNERABLE_TIME = takeNextMeta();
		public static final int WITHER_TARGET1 = takeNextMeta();
		public static final int WITHER_TARGET2 = takeNextMeta(); //50
		public static final int WITHER_TARGET3 = takeNextMeta();
		public static final int UNKOWN_8 = takeNextMeta();
		public static final int BOUNDINGBOX_WIDTH = takeNextMeta();
		public static final int BOUNDINGBOX_HEIGTH = takeNextMeta();
		public static final int FUSE_LENGTH = takeNextMeta();
		public static final int RIDER_POSITION = takeNextMeta();
		public static final int RIDER_LOCK = takeNextMeta();
		public static final int RIDER_MIN_ROTATION = takeNextMeta();
		public static final int RIDER_MAX_ROTATION = takeNextMeta();
		public static final int AREA_EFFECT_RADIUS = takeNextMeta(); //60
		public static final int AREA_EFFECT_WAITING = takeNextMeta();
		public static final int AREA_EFFECT_PARTICLE = takeNextMeta();
		public static final int SHULKER_HEIGHT = takeNextMeta();
		public static final int SHULKER_DIRECTION = takeNextMeta();
		public static final int SHULKER_SOMETHING = takeNextMeta();
		public static final int SHULKER_ATTACH_POS = takeNextMeta();
		public static final int TRADING_PLAYER = takeNextMeta();
		public static final int UNKNOWN_9 = takeNextMeta();
		public static final int COMMAND_BYTE = takeNextMeta();
		public static final int COMMAND_COMMAND = takeNextMeta(); //70
		public static final int COMMAND_LAST_OUTPUT = takeNextMeta();
		public static final int COMMAND_TRACK_OUTPUT = takeNextMeta();
		public static final int CONTROLLING_SEAT = takeNextMeta();
		public static final int STRENGTH = takeNextMeta();
		public static final int MAX_STRENGTH = takeNextMeta();
		public static final int UNKNOWN_10 = takeNextMeta();
		public static final int LIMITED_LIFE = takeNextMeta();
		public static final int ARMOUR_STAND_POSE = takeNextMeta();
		public static final int END_CRYSTAL_TIME = takeNextMeta();
		public static final int ALWAYS_SHOW_NAMETAG = takeNextMeta();
		public static final int COLOR_2 = takeNextMeta();
		public static final int UNKNOWN_11 = takeNextMeta();
		public static final int SCORE = takeNextMeta();
		public static final int BALLOON_ATTACHED = takeNextMeta();
		public static final int PUFFERFISH_SIZE = takeNextMeta();

	}
}
