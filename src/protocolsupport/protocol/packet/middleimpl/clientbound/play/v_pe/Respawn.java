package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.text.MessageFormat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Respawn extends MiddleRespawn {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHANGE_DIMENSION, version);
		VarNumberSerializer.writeSVarInt(serializer, remapDimensionId(dimension));
		MiscSerializer.writeLFloat(serializer, 0); //x
		MiscSerializer.writeLFloat(serializer, 0); //y
		MiscSerializer.writeLFloat(serializer, 0); //z
		serializer.writeBoolean(true); //unused value
		return RecyclableSingletonList.create(serializer);
	}

	public static int remapDimensionId(int dimId) {
		switch (dimId) {
			case -1: {
				return 1;
			}
			case 1: {
				return 2;
			}
			case 0: {
				return 0;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Uknown dim id {0}", dimId));
			}
		}
	}

}
