package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		for (int[] part : Utils.splitArray(entityIds, 120)) {
			ClientBoundPacketData entitydestroy = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY);
			entitydestroy.writeByte(part.length);
			for (int i = 0; i < part.length; i++) {
				entitydestroy.writeInt(part[i]);
			}
			codec.write(entitydestroy);
		}
	}

}
