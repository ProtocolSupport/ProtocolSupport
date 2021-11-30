package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2_18;

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
