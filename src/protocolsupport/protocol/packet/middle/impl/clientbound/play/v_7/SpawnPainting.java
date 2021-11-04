package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting implements IClientboundMiddlePacketV7 {

	public SpawnPainting(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		switch (direction) {
			case 0: {
				position.modifyZ(-1);
				break;
			}
			case 1: {
				position.modifyX(1);
				break;
			}
			case 2: {
				position.modifyZ(1);
				break;
			}
			case 3: {
				position.modifyX(-1);
				break;
			}
		}
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		StringCodec.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(type));
		PositionCodec.writePositionIII(spawnpainting, position);
		spawnpainting.writeInt(direction);
		io.writeClientbound(spawnpainting);
	}

}
