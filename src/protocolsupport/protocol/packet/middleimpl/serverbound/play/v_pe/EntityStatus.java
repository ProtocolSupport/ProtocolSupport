package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends ServerBoundMiddlePacket {

	public EntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	protected long entityId;
	protected byte status;
	protected int data;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readSVarLong(clientdata);
		status = clientdata.readByte();
		data = VarNumberSerializer.readSVarInt(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if ((status == 57) && (data != 0)) {
			//For some reason you need to resend the packet sent by the client to confirm he's eating.
			sendResponse(connection, (int) entityId, status, data);
		} else if (status == 62) {
			return RecyclableSingletonList.create(MiddleSelectTrade.create(data));
		}
		return RecyclableEmptyList.get();
	}

	protected static void sendResponse(Connection connection, int entityId, byte status, int data) {
		ByteBuf statusResponse = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(statusResponse, PEPacketIDs.ENTITY_EVENT);
		VarNumberSerializer.writeVarLong(statusResponse, entityId);
		statusResponse.writeByte(status);
		VarNumberSerializer.writeSVarInt(statusResponse, data);
		connection.sendRawPacket(MiscSerializer.readAllBytes(statusResponse));
	}

}
