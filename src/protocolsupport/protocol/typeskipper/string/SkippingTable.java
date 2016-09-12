package protocolsupport.protocol.typeskipper.string;

import java.util.HashSet;

public class SkippingTable {

	private final HashSet<String> skip = new HashSet<String>();

	public void setSkip(String id) {
		skip.add(id);
	}

	public boolean shouldSkip(String id) {
		return skip.contains(id);
	}

}
