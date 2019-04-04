package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_beta;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class ClientHandshake extends ServerBoundMiddlePacket {

	public ClientHandshake(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		StringSerializer.readString(clientdata, ProtocolVersion.getOldest(ProtocolType.PC));
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative()  {
		connection.getNetworkManagerWrapper().getChannel().eventLoop().schedule(() -> {
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createLoginEncryptionBeginPacket(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), new byte[0]));
		}, 1, TimeUnit.SECONDS);
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(creator, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(creator, "");
		creator.writeShort(Bukkit.getPort());
		VarNumberSerializer.writeVarInt(creator, 2);
		return RecyclableSingletonList.create(creator);
	}

}
