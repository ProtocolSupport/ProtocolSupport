package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

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
		spawnpainting.writeInt(entity.getId());
		StringCodec.writeShortUTF16BEString(spawnpainting, LegacyPainting.getName(type));
		PositionCodec.writePositionIII(spawnpainting, position);
		spawnpainting.writeInt(direction);
		io.writeClientbound(spawnpainting);
	}

}
