package protocolsupport.protocol.typeremapper.itemstack.complex;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.NetworkItemStack;

@FunctionalInterface
public interface ItemStackComplexRemapper {

	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack);

}
