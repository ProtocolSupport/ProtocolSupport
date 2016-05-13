package protocolsupport.protocol.packet.mcpe.packet.mcpe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import gnu.trove.map.hash.TByteObjectHashMap;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.AnimatePacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.BatchPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.ContainerClosePacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.ContainerSetSlotPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.EntityEquipmentArmorPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.EntityEquipmentInventoryPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.PingPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.PongPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.SetHealthPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.AddExpOrbPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.ChunkRadiusRequest;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.ClientConnectPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.ClientDisconnectPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.ClientHandshakePacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.CraftPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.DropItemPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.LoginPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.PlayerActionPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.RemoveBlockPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.UseEntityPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound.UseItemPacket;

public class PEPacketRegistry {

	private static final TByteObjectHashMap<Constructor<? extends ServerboundPEPacket>> packets = new TByteObjectHashMap<Constructor<? extends ServerboundPEPacket>>();

	static {
		register(BatchPacket.class);
		register(ChatPacket.class);
		register(ClientConnectPacket.class);
		register(ClientHandshakePacket.class);
		register(LoginPacket.class);
		register(PingPacket.class);
		register(PongPacket.class);
		register(ClientDisconnectPacket.class);
		register(MovePlayerPacket.class);
		register(UseItemPacket.class);
		register(RemoveBlockPacket.class);
		register(PlayerActionPacket.class);
		register(AnimatePacket.class);
		register(SetHealthPacket.class);
		register(EntityEquipmentInventoryPacket.class);
		register(EntityEquipmentArmorPacket.class);
		register(ContainerSetSlotPacket.class);
		register(DropItemPacket.class);
		register(UseEntityPacket.class);
		register(CraftPacket.class);
		register(AddExpOrbPacket.class);
		register(ContainerClosePacket.class);
		register(ChunkRadiusRequest.class);
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
