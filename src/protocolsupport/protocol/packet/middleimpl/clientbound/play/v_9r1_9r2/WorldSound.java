package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String soundname = SoundRemapper.getSoundName(version, id);
		if (soundname != null) {
			ClientBoundPacketData worldsound = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND);
			StringSerializer.writeVarIntUTF8String(worldsound, soundname);
			VarNumberSerializer.writeVarInt(worldsound, category);
			worldsound.writeInt(x);
			worldsound.writeInt(y);
			worldsound.writeInt(z);
			worldsound.writeFloat(volume);
			worldsound.writeByte((int) (pitch * 63.5));
			codec.write(worldsound);
		}
	}

}
