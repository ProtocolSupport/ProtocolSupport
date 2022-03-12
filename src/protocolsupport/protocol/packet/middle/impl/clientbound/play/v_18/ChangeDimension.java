package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class ChangeDimension extends MiddleChangeDimension implements IClientboundMiddlePacketV18 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		ItemStackCodec.writeDirectTag(changedimension, version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_18) ? StartGame.toLegacyDimensionType(dimension) : dimension);
		StringCodec.writeVarIntUTF8String(changedimension, world);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeByte(gamemodePrevious.getId());
		changedimension.writeBoolean(worldDebug);
		changedimension.writeBoolean(worldFlat);
		changedimension.writeBoolean(keepEntityMetadata);
		io.writeClientbound(changedimension);
	}

}
