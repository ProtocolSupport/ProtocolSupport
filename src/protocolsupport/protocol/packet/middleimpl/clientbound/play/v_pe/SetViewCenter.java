package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetViewCenter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetViewCenter extends MiddleSetViewCenter {

	public SetViewCenter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(createChunkPublisherUpdate(
			chunk.getX() * 16, 64, chunk.getZ() * 16));
	}

	public static void writeChunkPublisherUpdate(ByteBuf out, int x, int y, int z) {
		VarNumberSerializer.writeSVarInt(out, x);
		VarNumberSerializer.writeVarInt(out, y);
		VarNumberSerializer.writeSVarInt(out, z);
		VarNumberSerializer.writeVarInt(out, 512); //radius, gets clamped by client
	}

	public static ClientBoundPacketData createChunkPublisherUpdate(int x, int y, int z) {
		ClientBoundPacketData networkChunkUpdate = ClientBoundPacketData.create(PEPacketIDs.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET);
		writeChunkPublisherUpdate(networkChunkUpdate, x, y, z);
		return networkChunkUpdate;
	}
}
