package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleLoginCustomPayload extends ClientBoundMiddlePacket {

	public MiddleLoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected String tag;
	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		tag = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		data = MiscSerializer.readAllBytes(serverdata);
	}

}
