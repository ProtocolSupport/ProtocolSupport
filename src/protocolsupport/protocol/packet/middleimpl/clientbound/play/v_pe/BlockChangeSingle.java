package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int flag_update_neighbors = 0b0001;
	protected static final int flag_network = 0b0010;
	protected static final int flag_nographic = 0b0100;
	protected static final int flag_priority = 0b1000;

	protected static final int flags = (flag_update_neighbors | flag_network | flag_priority);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(BlockChangeSingle.create(connection.getVersion(), position, id));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, Position position, int state) {
		state = PEDataValues.BLOCK_ID.getRemap(state);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK);
		PositionSerializer.writePEPosition(serializer, position);
		VarNumberSerializer.writeVarInt(serializer, state);
		VarNumberSerializer.writeVarInt(serializer, flags);
		VarNumberSerializer.writeVarInt(serializer, 0); //Normal layer (liquid not implemented in java yet)
		return serializer;
	}

}
