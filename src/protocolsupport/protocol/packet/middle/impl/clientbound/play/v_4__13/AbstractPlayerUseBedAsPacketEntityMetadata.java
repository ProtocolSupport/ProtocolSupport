package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractLegacyPaintingEntityMetadata;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;

public abstract class AbstractPlayerUseBedAsPacketEntityMetadata extends AbstractLegacyPaintingEntityMetadata {

	protected AbstractPlayerUseBedAsPacketEntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	public void write() {
		super.write();

		if (lType == NetworkEntityType.PLAYER) {
			NetworkEntityMetadataObjectOptionalPosition bedpositionObject = NetworkEntityMetadataObjectIndexRegistry.EntityLivingIndexRegistry.INSTANCE.BED_LOCATION.getObject(metadata);
			if (bedpositionObject != null) {
				Position bedposition = bedpositionObject.getValue();
				if (bedposition != null) {
					writePlayerUseBed(bedposition);
				}
			}
		}
	}

	protected abstract void writePlayerUseBed(Position position);

}
