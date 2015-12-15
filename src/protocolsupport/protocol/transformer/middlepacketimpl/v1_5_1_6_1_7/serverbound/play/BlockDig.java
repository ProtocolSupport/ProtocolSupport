package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleBlockDig;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		status = serializer.readUnsignedByte();
		position = new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt());
		face = serializer.readUnsignedByte();
	}

}
