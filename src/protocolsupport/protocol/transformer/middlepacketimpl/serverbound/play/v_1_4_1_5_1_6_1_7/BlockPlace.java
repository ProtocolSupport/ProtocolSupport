package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleBlockPlace;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		position = new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt());
		face = serializer.readUnsignedByte();
		itemstack = serializer.readItemStack();
		cX = serializer.readUnsignedByte();
		cY = serializer.readUnsignedByte();
		cZ = serializer.readUnsignedByte();
	}

}
