package protocolsupport.zmcpe.packetsimpl.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class Position extends MiddlePosition<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_MOVE, version);
		serializer.writeSVarLong(0); //player entity id (0 for self)
		serializer.writeLFloat((float) x);
		serializer.writeLFloat((float) y + 1.6200000047683716F);
		serializer.writeLFloat((float) z);
		serializer.writeLFloat(pitch);
		serializer.writeLFloat(yaw);
		serializer.writeLFloat(yaw); //head yaw actually
		serializer.writeByte(1); //animation mode, 1 none
		serializer.writeBoolean(true);
		return RecyclableSingletonList.create(serializer);
	}

}
