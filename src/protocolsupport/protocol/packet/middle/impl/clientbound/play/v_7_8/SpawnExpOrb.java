package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7_8;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;

public class SpawnExpOrb extends MiddleSpawnExpOrb implements
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public SpawnExpOrb(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnexporb = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_EXP_ORB);
		VarNumberCodec.writeVarInt(spawnexporb, entity.getId());
		spawnexporb.writeInt((int) (x * 32));
		spawnexporb.writeInt((int) (y * 32));
		spawnexporb.writeInt((int) (z * 32));
		spawnexporb.writeShort(count);
		io.writeClientbound(spawnexporb);
	}

}
