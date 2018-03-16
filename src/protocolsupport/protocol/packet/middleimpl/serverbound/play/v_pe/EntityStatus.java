package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends ServerBoundMiddlePacket {

	protected long entityId;
	protected byte status;
	protected int data;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readSVarLong(clientdata);
		status = clientdata.readByte();
		data = VarNumberSerializer.readSVarInt(clientdata);
		System.out.println("ENTITYEVENT!!! " + entityId + " - " + status + " - " + data);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (status == 57 && data != 0) {
			//Somewhy you need to resent the packet send by the client to confirm he's eating.
			sendResponse(connection, (int) entityId, status, data);
		} else if (status == 62) {
			return RecyclableSingletonList.create(TradeSelect(data));
		}
		return RecyclableEmptyList.get();
	}
	
	private static ServerBoundPacketData TradeSelect(int page) {
		ByteBuf payload = Unpooled.buffer();
		payload.writeInt(page);
		return MiddleCustomPayload.create("MC|TrSel", MiscSerializer.readAllBytes(payload));
	}
	
	public static void sendResponse(Connection connection, int entityId, byte status, int data) {
		ByteBuf statusResponse = Unpooled.buffer(); //Can't use normal packet, we need data too.
		VarNumberSerializer.writeVarInt(statusResponse, PEPacketIDs.ENTITY_EVENT);
		statusResponse.writeByte(0);
		statusResponse.writeByte(0);
		VarNumberSerializer.writeVarLong(statusResponse, entityId);
		statusResponse.writeByte(status);
		VarNumberSerializer.writeSVarInt(statusResponse, data);
		connection.sendRawPacket(MiscSerializer.readAllBytes(statusResponse));
	}

}
