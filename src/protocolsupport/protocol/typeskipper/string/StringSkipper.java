package protocolsupport.protocol.typeskipper.string;

import protocolsupport.utils.ProtocolVersionsHelper;

public class StringSkipper {

	public static final SkippingRegistry ATTRIBUTES = new SkippingRegistry() {
		{
			registerSkipEntry("generic.armor", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.attackSpeed ", ProtocolVersionsHelper.BEFORE_1_9);
		}
	};

}
