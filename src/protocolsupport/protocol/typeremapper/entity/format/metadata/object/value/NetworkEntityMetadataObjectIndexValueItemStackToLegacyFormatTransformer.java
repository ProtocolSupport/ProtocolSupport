package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;

public class NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectItemStack> {

	protected final ProtocolVersion version;

	public NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> fromIndex, int toIndex, ProtocolVersion version) {
		super(fromIndex, toIndex);
		this.version = version;
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectItemStack object) {
		return new NetworkEntityMetadataObjectItemStack(ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, object.getValue().clone()));
	}

}
