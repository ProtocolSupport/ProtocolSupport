package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__17r2;

import java.util.BitSet;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.types.chunk.IPalettedStorage;
import protocolsupport.protocol.types.chunk.PalettedStorageSingle;

public abstract class AbstractMaskChunkData extends MiddleChunkData {

	protected AbstractMaskChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected BitSet mask;

	@Override
	protected void handle() {
		mask = new BitSet(sections.length);
		for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
			IPalettedStorage palettedStorage = sections[sectionIndex].getBlockData();
			if (!(palettedStorage instanceof PalettedStorageSingle palettedStorageSingle) || (palettedStorageSingle.getId() != 0)) {
				mask.set(sectionIndex);
			}
		}
	}

}
