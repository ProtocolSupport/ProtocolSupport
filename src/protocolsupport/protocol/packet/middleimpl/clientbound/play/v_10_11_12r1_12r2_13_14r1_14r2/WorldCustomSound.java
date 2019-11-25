package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;

public class WorldCustomSound extends MiddleWorldCustomSound {

	public WorldCustomSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData worldcustomsound = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND);
		StringSerializer.writeVarIntUTF8String(worldcustomsound, SoundRemapper.getSoundName(version, id));
		VarNumberSerializer.writeVarInt(worldcustomsound, category);
		worldcustomsound.writeInt(x);
		worldcustomsound.writeInt(y);
		worldcustomsound.writeInt(z);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeFloat(pitch);
		codec.write(worldcustomsound);
	}

}
