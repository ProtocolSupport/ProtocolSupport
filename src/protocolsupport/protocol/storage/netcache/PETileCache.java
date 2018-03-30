package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PETileCache {

	private NBTTagCompoundWrapper signTag = null;

	public void updateSignTag(NBTTagCompoundWrapper signTag) {
		this.signTag = signTag;
	}

	public boolean shouldSignSign() {
		return signTag != null;
	}

	public void signSign(RecyclableCollection<ServerBoundPacketData> packets) {
		int x = signTag.getIntNumber("x");
		int y = signTag.getIntNumber("y");
		int z = signTag.getIntNumber("z");
		String[] nbtLines = new String[4];
		String[] lines = signTag.getString("Text").split("\n");
		for (int i = 0; i < nbtLines.length; i++) {
			if (lines.length > i) {
				nbtLines[i] = lines[i];
			} else {
				nbtLines[i] = "";
			}
		}
		signTag = null;
		packets.add(MiddleUpdateSign.create(new Position(x, y, z), nbtLines));
	}

}
