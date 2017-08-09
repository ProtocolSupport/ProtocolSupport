package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.protocol.utils.types.WindowType;

//Slots are different in PE. That's absolutely great! ;/
//This class houses some run of the mill hacky, slashy functions to compensate slots and simulate PC inventory clicking behavior.
//Buyers beware!
public class PEInventory {
	
	public static int remapClientboundSlot(WindowType windowType, int slot) {
		if(windowType == WindowType.PLAYER && slot >= 36) {
			return slot - 36;
		}
		return slot;
	}
	
	public static int remapServerboundSlot(WindowType windowType, int slot) {
		if (windowType == WindowType.PLAYER && slot < 9) {
			return slot + 36;
		}
		return slot;
	}
	
}
