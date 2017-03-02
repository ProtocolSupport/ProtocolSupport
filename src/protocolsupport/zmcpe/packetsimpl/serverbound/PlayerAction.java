package protocolsupport.zmcpe.packetsimpl.serverbound;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
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
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readVarLong(); //entity id
		action = serializer.readSVarInt();
		blockX = serializer.readSVarInt();
		blockY = serializer.readVarInt();
		blockZ = serializer.readSVarInt();
		face = serializer.readSVarInt();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (action) {
			case 7: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				serializer.writeVarInt(0);
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
