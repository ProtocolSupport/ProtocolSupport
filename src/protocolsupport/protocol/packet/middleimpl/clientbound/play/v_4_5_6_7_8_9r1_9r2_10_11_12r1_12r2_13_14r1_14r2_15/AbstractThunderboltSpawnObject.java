package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractRemappedSpawnObject;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractThunderboltSpawnObject extends AbstractRemappedSpawnObject {

	protected AbstractThunderboltSpawnObject(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (fType != NetworkEntityType.THUNDERBOLT) {
			writeSpawnObject();
		} else {
			writeSpawnThunderbolt();
		}
	}

	protected abstract void writeSpawnObject();

	protected abstract void writeSpawnThunderbolt();

}
