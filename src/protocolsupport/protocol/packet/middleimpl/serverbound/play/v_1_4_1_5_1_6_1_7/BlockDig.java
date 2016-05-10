package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		status = serializer.readUnsignedByte();
		position = new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt());
		face = serializer.readUnsignedByte();
	}

}
