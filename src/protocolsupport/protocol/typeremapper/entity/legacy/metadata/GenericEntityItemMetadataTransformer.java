package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class GenericEntityItemMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	protected final ProtocolVersion version;
	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> index;

	public GenericEntityItemMetadataTransformer(ProtocolVersion version, NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> index) {
		this.version = version;
		this.index = index;
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectItemStack itemstackObject = index.getObject(t);
		if (itemstackObject != null) {
			itemstackObject.setValue(ItemStackRemappingHelper.toLegacyItemData(version, I18NData.DEFAULT_LOCALE, itemstackObject.getValue()));
		}
	}

}