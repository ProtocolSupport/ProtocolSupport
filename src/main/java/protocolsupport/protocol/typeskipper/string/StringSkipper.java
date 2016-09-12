package protocolsupport.protocol.typeskipper.string;

import protocolsupport.utils.ProtocolVersionsHelper;

public class StringSkipper {

	public static final SkippingRegistry ATTRIBUTES = new SkippingRegistry() {
		{
			registerSkipEntry("generic.armorToughness", ProtocolVersionsHelper.BEFORE_1_9_1);
			registerSkipEntry("generic.luck", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.armor", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.attackSpeed", ProtocolVersionsHelper.BEFORE_1_9);
		}
	};

	public static void init() {
	}

}
