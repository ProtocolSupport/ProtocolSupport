package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__6;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class SpawnPosition extends MiddleSpawnPosition implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public SpawnPosition(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnposition = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_POSITION);
		position.modifyY(1);
		PositionCodec.writePositionIII(spawnposition, position);
		io.writeClientbound(spawnposition);
	}

}
