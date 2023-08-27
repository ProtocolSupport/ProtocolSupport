package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import java.util.concurrent.TimeUnit;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractPlayerUseBedAsPacketEntityMetadata;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.utils.PrimitiveTypeUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.Vector3S;

public abstract class AbstractLegacyPotionItemEntityMetadata extends AbstractPlayerUseBedAsPacketEntityMetadata {

	protected AbstractLegacyPotionItemEntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	public void write() {
		if (fType == NetworkEntityType.POTION) {
			NetworkEntityDataCache edata = entity.getDataCache();
			PotionNetworkEntityData potiondata = edata.getData(PotionNetworkEntityData.DATA_KEY);
			if (potiondata != null) {
				potiondata.updateMetadata(metadata);
				NetworkEntityMetadataObjectItemStack item = NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry.INSTANCE.ITEM.getObject(metadata);
				if (item != null) {
					fMetadata.clear();
					writePotionRespawn(item.getValue(), potiondata.getVelocity());
					NetworkEntityTransformHelper.transformMetadataFormat(entity, potiondata.getMetadata(), entityLegacyFormatTable.get(fType), fMetadata);
					writeEntityMetadata(fMetadata);
					return;
				}
			}
		}

		super.write();
	}

	protected abstract void writePotionRespawn(NetworkItemStack item, Vector3S velocity);


	public static class PotionNetworkEntityData {

		public static final String DATA_KEY = "Potion_VelMetaData";

		protected static final double mul_horizontal = 0.99;
		protected static final short dec_vertical = (short) PrimitiveTypeUtils.toFixedPoint8K(0.03);

		protected static final double[] mul_horizontal_10 = computeMulHTable();

		protected static double[] computeMulHTable() {
			double mul = Math.pow(mul_horizontal, 10);
			double[] table = new double[10];
			for (int i = 0; i < table.length; i++) {
				table[i] = mul;
				mul *= mul;
			}
			return table;
		}

		protected short velX;
		protected short velY;
		protected short velZ;
		protected long velTimestamp;
		protected int velYMaxDecTicks;

		protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(10);

		public void updateVelocity(short velX, short velY, short velZ) {
			this.velX = velX;
			this.velY = velY;
			this.velZ = velZ;
			this.velYMaxDecTicks = (32767 + velY) / dec_vertical;
			this.velTimestamp = System.nanoTime();
		}

		/*
		 * Vanilla multiplies velocity on x,z by 0.99 and decreases y by 0.03F each tick
		 * Calculating 0.99 multiplication for each tick passed isn't really worth it, because each tick change is small,
		 *   so x,z multiplier is calculated for 10 each ticks passed instead and uses computed table, which is limited to 10 seconds worth of ticks
		 * If more than 10 seconds passed since the last velocity update, a constant x,z divisor of 10 is used
		 * If more than 20 seconds passed since the last velocity update, x,z is set to 0
		 */
		public Vector3S getVelocity() {
			int ticks = (int) (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - velTimestamp) / 50);

			if (ticks < 0) {
				return Vector3S.ZERO;
			}

			if (ticks < 10) {
				return new Vector3S(velX, velY, velZ);
			}

			short cVelY = (short) (ticks < velYMaxDecTicks ? velY - (ticks * dec_vertical) : Short.MIN_VALUE);

			int ticks10 = (ticks / 10) - 1;
			if (ticks10 < mul_horizontal_10.length) {
				double mul = mul_horizontal_10[ticks10];
				return new Vector3S((short) (velX * mul), cVelY, (short) (velZ * mul));
			} else if (ticks10 < 40) {
				return new Vector3S((short) (velX / 10), cVelY, (short) (velZ / 10));
			} else {
				return new Vector3S((short) 0, cVelY, (short) 0);
			}
		}

		public void updateMetadata(ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
			NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry registry = NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry.INSTANCE;
			registry.BASE_FLAGS.copy(metadata, this.metadata);
			registry.AIR.copy(metadata, this.metadata);
			registry.NAMETAG.copy(metadata, this.metadata);
			registry.NAMETAG_VISIBLE.copy(metadata, this.metadata);
			registry.SILENT.copy(metadata, this.metadata);
			registry.NO_GRAVITY.copy(metadata, this.metadata);
			registry.POSE.copy(metadata, this.metadata);
			registry.ITEM.copy(metadata, this.metadata);
		}

		public ArrayMap<NetworkEntityMetadataObject<?>> getMetadata() {
			return metadata;
		}

	}

}
