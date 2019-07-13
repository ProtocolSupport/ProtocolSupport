package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PETileCache {

	private NBTCompound signTag = null;

	public void updateSignTag(NBTCompound signTag) {
		this.signTag = signTag;
	}

	public boolean shouldSignSign() {
		return signTag != null;
	}

	public void signSign(RecyclableCollection<ServerBoundPacketData> packets) {
		int x = signTag.getNumberTag("x").getAsInt();
		int y = signTag.getNumberTag("y").getAsInt();
		int z = signTag.getNumberTag("z").getAsInt();
		String[] nbtLines = new String[4];
		String[] lines = signTag.getTagOfType("Text", NBTType.STRING).getValue().split("\n");
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
