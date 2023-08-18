package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18;

import org.bukkit.util.NumberConversions;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractRemappedEntityMetadata;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class AbstractLegacyPaintingEntityMetadata extends AbstractRemappedEntityMetadata {

	protected AbstractLegacyPaintingEntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	public void write() {
		if (fType == NetworkEntityType.PAINTING) {
			NetworkEntityDataCache edata = entity.getDataCache();
			PaintingNetworkEntityData paintingdata = edata.getData(PaintingNetworkEntityData.DATA_KEY);
			if (paintingdata != null) {
				paintingdata.updateMetadata(metadata);
				NetworkEntityMetadataObjectVarInt variant = NetworkEntityMetadataObjectIndex.Painting.VARIANT.getObject(metadata);
				if (variant != null) {
					fMetadata.clear();
					writePaintingRespawn(paintingdata.getPosition(), paintingdata.getDirection(), variant.getValue());
					NetworkEntityTransformHelper.transformMetadataFormat(entity, paintingdata.getMetadata(), entityLegacyFormatTable.get(fType), fMetadata);
					writeEntityMetadata(fMetadata);
					return;
				}
			}
		}

		writeEntityMetadata(fMetadata);
	}

	protected abstract void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata);

	protected abstract void writePaintingRespawn(Position position, int direction, int variant);



	public static class PaintingNetworkEntityData {

		public static final String DATA_KEY = "Painting_MetaData";

		protected final Position position;
		protected final int direction;
		protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(10);

		public PaintingNetworkEntityData(double x, double y, double z, int direction) {
			this.position = new Position(NumberConversions.floor(x), NumberConversions.floor(y), NumberConversions.floor(z));
			this.direction = direction;
		}

		public Position getPosition() {
			return position;
		}

		public int getDirection() {
			return direction;
		}

		public ArrayMap<NetworkEntityMetadataObject<?>> getMetadata() {
			return metadata;
		}

		public void updateMetadata(ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
			NetworkEntityMetadataObjectIndex.Entity.BASE_FLAGS.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.AIR.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.NAMETAG.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.SILENT.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Entity.POSE.copy(metadata, this.metadata);
			NetworkEntityMetadataObjectIndex.Potion.ITEM.copy(metadata, this.metadata);
		}


	}

}
