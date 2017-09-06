package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ModalResponse extends ClientBoundMiddlePacket {

	protected int modalId;
	protected String modalJSON;
	
	@Override
	public void readFromServerData(ByteBuf serverdata) {
		this.modalId = serverdata.readInt();
		this.modalJSON = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(modalId, modalJSON, connection.getVersion()));
	}
	
	public static ClientBoundPacketData create(int modalId, String modalJSON, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MODAL_RESPONSE, version);
		VarNumberSerializer.writeVarInt(serializer, modalId);
		StringSerializer.writeString(serializer, version, modalJSON);
		return serializer;
	}

}
