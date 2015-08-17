package protocolsupport.protocol.transformer.mcpe.packet.mcpe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.AnimatePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.BatchPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.PlayerActionPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.SetHealthPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.ClientConnectPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.ClientDisconnectPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.ClientHandshakePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.LoginPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.PingPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.RemoveBlockPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.UseItemPacket;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PEPacketRegistry {

	private static final TByteObjectHashMap<Constructor<? extends ServerboundPEPacket>> packets = new TByteObjectHashMap<Constructor<? extends ServerboundPEPacket>>();

	static {
		register(BatchPacket.class);
		register(ChatPacket.class);
		register(ClientConnectPacket.class);
		register(ClientHandshakePacket.class);
		register(LoginPacket.class);
		register(PingPacket.class);
		register(ClientDisconnectPacket.class);
		register(MovePlayerPacket.class);
		register(UseItemPacket.class);
		register(RemoveBlockPacket.class);
		register(PlayerActionPacket.class);
		register(AnimatePacket.class);
		register(SetHealthPacket.class);
	}

	public static ServerboundPEPacket getPacket(int id) {
		try {
			return packets.get((byte) id).newInstance();
		} catch (NullPointerException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalArgumentException("Packet id "+id+" doesn't exist", e);
		}
	}

	private static void register(Class<? extends ServerboundPEPacket> clazz) {
		try {
			PEPacket pepacket = clazz.newInstance();
			packets.put((byte) pepacket.getId(), clazz.getConstructor());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			System.err.println("Failed to register packet "+clazz.getName());
			e.printStackTrace();
		}
	}

}
