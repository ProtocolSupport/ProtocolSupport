package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends AbstractSneakingCacheUseEntity {

	public UseEntity(ConnectionImpl connection) {
		super(connection);
		hand = UsedHand.MAIN;
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = MiscSerializer.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
	}

}
