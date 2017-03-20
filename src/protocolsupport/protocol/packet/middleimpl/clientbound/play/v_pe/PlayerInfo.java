package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.legacyremapper.pe.PESkin;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerInfo extends MiddlePlayerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		switch (action) {
			case ADD: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(0);
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, info.uuid);
					VarNumberSerializer.writeSVarInt(serializer, 0); //entity id
					StringSerializer.writeString(serializer, version, info.getName());
					StringSerializer.writeString(serializer, version, "Standard_Steve");
					ByteArraySerializer.writeByteArray(serializer, version, PESkin.STEVE);
				}
				return RecyclableSingletonList.create(serializer);
			}
			case REMOVE: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(1);
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, info.uuid);
				}
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
