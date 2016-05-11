package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.BlockPosition;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		position = new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt());
		face = serializer.readByte();
		serializer.readItemStack();
		cX = serializer.readUnsignedByte();
		cY = serializer.readUnsignedByte();
		cZ = serializer.readUnsignedByte();
	}

}
