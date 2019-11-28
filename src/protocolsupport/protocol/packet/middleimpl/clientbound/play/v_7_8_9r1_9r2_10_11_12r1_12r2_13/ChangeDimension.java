package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChangeDimension;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Difficulty;

public class ChangeDimension extends AbstractChangeDimension {

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESPAWN);
		changedimension.writeInt(dimension.getId());
		MiscSerializer.writeByteEnum(changedimension, Difficulty.HARD);
		changedimension.writeByte(gamemode.getId());
		StringSerializer.writeVarIntUTF8String(changedimension, leveltype);
		codec.write(changedimension);
	}

}
