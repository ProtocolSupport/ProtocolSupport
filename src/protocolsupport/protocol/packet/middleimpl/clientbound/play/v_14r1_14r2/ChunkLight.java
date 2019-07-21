package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ChunkLight extends MiddleChunkLight {

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_LIGHT);
		PositionSerializer.writeVarIntChunkCoord(serializer, coord);
		VarNumberSerializer.writeVarInt(serializer, setSkyLightMask);
		VarNumberSerializer.writeVarInt(serializer, setBlockLightMask);
		VarNumberSerializer.writeVarInt(serializer, emptySkyLightMask);
		VarNumberSerializer.writeVarInt(serializer, emptyBlockLightMask);
		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			if (Utils.isBitSet(setSkyLightMask, i)) {
				ArraySerializer.writeVarIntByteArray(serializer, skyLight[i]);
			}
		}
		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			if (Utils.isBitSet(setBlockLightMask, i)) {
				ArraySerializer.writeVarIntByteArray(serializer, blockLight[i]);
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
