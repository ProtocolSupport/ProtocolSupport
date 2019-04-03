package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginSuccess extends MiddleLoginSuccess {

	public LoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	public static final int LOGIN_SUCCESS = 0;
	public static final int PLAYER_SPAWN = 3;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(createPlayStatus(LOGIN_SUCCESS));
	}

	public static ClientBoundPacketData createPlayStatus(int status) {
		ClientBoundPacketData playstatus = ClientBoundPacketData.create(PEPacketIDs.PLAY_STATUS);
		playstatus.writeInt(status);
		return playstatus;
	}

	public static byte[] createRawPlayStatus(int status) {
		final ByteBuf out = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(out, PEPacketIDs.PLAY_STATUS);
		out.writeInt(status);
		return MiscSerializer.readAllBytes(out);
	}

}
