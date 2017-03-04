package protocolsupport.zmcpe.packetsimpl.serverbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAction extends ServerBoundMiddlePacket {

	protected int action;
	protected int blockX;
	protected int blockY;
	protected int blockZ;
	protected int face;

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		VarNumberSerializer.readVarLong(clientdata); //entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		blockX = VarNumberSerializer.readSVarInt(clientdata);
		blockY = VarNumberSerializer.readVarInt(clientdata);
		blockZ = VarNumberSerializer.readSVarInt(clientdata);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (action) {
			case 7: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				VarNumberSerializer.writeSVarInt(serializer, 0);
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
