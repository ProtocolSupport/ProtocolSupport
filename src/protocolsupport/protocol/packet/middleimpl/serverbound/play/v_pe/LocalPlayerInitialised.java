package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LocalPlayerInitialised extends ServerBoundMiddlePacket {

	public LocalPlayerInitialised(ConnectionImpl connection) {
		super(connection);
	}

	protected long entityId;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(MiddleCustomPayload.create(InternalPluginMessageRequest.PEUnlockChannel));
	}

}
