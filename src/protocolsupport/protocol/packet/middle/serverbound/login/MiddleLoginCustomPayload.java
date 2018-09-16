package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleLoginCustomPayload extends ServerBoundMiddlePacket {

	public MiddleLoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected ByteBuf data;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(creator, id);
		creator.writeBoolean(data != null);
		if (data != null) {
			creator.writeBytes(data);
		}
		return RecyclableSingletonList.create(creator);
	}

}
