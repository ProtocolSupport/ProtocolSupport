package protocolsupport.protocol.typeremapper.itemstack.legacy;

import java.util.function.UnaryOperator;

import protocolsupport.protocol.types.NetworkItemStack;

public class ItemStackLegacyDataTypeMappingOperator implements UnaryOperator<NetworkItemStack> {

	protected final int typeId;

	public ItemStackLegacyDataTypeMappingOperator(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public NetworkItemStack apply(NetworkItemStack t) {
		t.setTypeId(typeId);
		return t;
	}

}