package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractRemappedEntityMetadata;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;

public abstract class AbstractPlayerUseBedAsPacketEntityMetadata extends AbstractRemappedEntityMetadata {

	protected AbstractPlayerUseBedAsPacketEntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	@Override
	public void write() {
		writeEntityMetadata(fMetadata);

		if (lType == NetworkEntityType.PLAYER) {
			NetworkEntityMetadataObjectOptionalPosition bedpositionObject = NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION.getObject(metadata);
			if (bedpositionObject != null) {
				Position bedposition = bedpositionObject.getValue();
				if (bedposition != null) {
					writePlayerUseBed(bedposition);
				}
			}
		}
	}

	protected abstract void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata);

	protected abstract void writePlayerUseBed(Position position);

}
