package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Animation extends MiddleAnimation {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		switch (animation) {
			case 0: {
				return RecyclableSingletonList.create(create(version, entityId, 1));
			}
			case 2: {
				return RecyclableSingletonList.create(create(version, entityId, 3));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

	private static ClientBoundPacketData create(ProtocolVersion version, int entityId, int animation) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ANIMATION, version);
		VarNumberSerializer.writeSVarInt(serializer, animation);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		return serializer;
	}

}
