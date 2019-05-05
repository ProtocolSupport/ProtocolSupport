package protocolsupport.protocol.typeremapper.itemstack.complex;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.NetworkItemStack;

@FunctionalInterface
public interface ItemStackComplexRemapper {

	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack);

}
