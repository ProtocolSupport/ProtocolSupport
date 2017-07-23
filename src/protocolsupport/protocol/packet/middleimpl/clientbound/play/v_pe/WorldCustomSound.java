package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldCustomSound extends MiddleWorldCustomSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAY_SOUND, version);
		StringSerializer.writeString(serializer, version, id);
		VarNumberSerializer.writeSVarInt(serializer, x);
		VarNumberSerializer.writeVarInt(serializer, y);
		VarNumberSerializer.writeSVarInt(serializer, z);
		MiscSerializer.writeLFloat(serializer, volume);
		MiscSerializer.writeLFloat(serializer, pitch);
		return RecyclableSingletonList.create(serializer);
	}

}