package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.handler.PELoginListener;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.Packet;

public class LoginPacket implements ServerboundPEPacket {

	protected String username;
	protected int protocol1;
	protected int protocol2;
	protected UUID uuid;
	protected long clientID;
	protected String hostname;

	protected boolean slim;
	protected byte[] skin;

	@Override
	public int getId() {
		return PEPacketIDs.LOGIN_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		username = serializer.readString();
		protocol1 = serializer.readInt();
		protocol2 = serializer.readInt();
		clientID = serializer.readLong();
		uuid = serializer.readUUID();
		hostname = serializer.readString();
		serializer.readArray(); //some sort of key for future auth maybe?
		slim = serializer.readBoolean();
		skin = serializer.readArray();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() {
		return Collections.singletonList(new HandleNMSPacket<PELoginListener>() {
			@Override
			public void handle(PELoginListener listener) {
				listener.handleLoginPacket(LoginPacket.this);
			}
		});
	}

	public String getUserName() {
		return username;
	}

}
