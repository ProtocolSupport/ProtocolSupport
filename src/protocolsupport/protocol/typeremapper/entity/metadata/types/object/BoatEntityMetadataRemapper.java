package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class BoatEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public BoatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Boat.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Boat.LEFT_PADDLE, PeMetaBase.PADDLE_TIME_LEFT) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				//TODO: Actually increment and send correct 'paddletime'.
				return new NetworkEntityMetadataObjectFloatLe(object.getValue() ? 0.05f: 0f);
		}}, ProtocolVersionsHelper.ALL_PE);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Boat.RIGHT_PADDLE, PeMetaBase.PADDLE_TIME_RIGHT) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				//TODO: Actually increment and send correct 'paddletime'.
				return new NetworkEntityMetadataObjectFloatLe(object.getValue() ? 0.05f: 0f);
		}}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.TIME_SINCE_LAST_HIT, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.TIME_SINCE_LAST_HIT, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.TIME_SINCE_LAST_HIT, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Boat.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.FORWARD_DIRECTION, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.FORWARD_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.FORWARD_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Boat.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.DAMAGE_TAKEN, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.VARIANT, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.VARIANT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.LEFT_PADDLE, 11), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.LEFT_PADDLE, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.RIGHT_PADDLE, 12), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.RIGHT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.SPLASH_TIMER, 13), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Boat.SPLASH_TIMER, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
	}

}
