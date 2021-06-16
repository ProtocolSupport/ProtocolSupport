package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCamera;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class Camera extends MiddleCamera {

	public Camera(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData camera = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CAMERA);
		VarNumberSerializer.writeVarInt(camera, entityId);
		codec.writeClientbound(camera);
	}

}
