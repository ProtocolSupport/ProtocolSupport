package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		for (int[] partEntityIds : Utils.splitArray(entityIds, 120)) {
			codec.write(create(partEntityIds));
		}
	}

	public static ClientBoundPacketData create(int... entityIds) {
		ClientBoundPacketData entitydestroy = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY);
		entitydestroy.writeByte(entityIds.length);
		for (int i = 0; i < entityIds.length; i++) {
			entitydestroy.writeInt(entityIds[i]);
		}
		return entitydestroy;
	}

}
