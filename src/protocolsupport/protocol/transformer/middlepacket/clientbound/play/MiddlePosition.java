package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_9_R1.CancelledPacketHandleException;
import net.minecraft.server.v1_9_R1.PacketPlayInTeleportAccept;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;

public abstract class MiddlePosition<T> extends ClientBoundMiddlePacket<T> {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected int teleportConfirmId;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readFloat();
		pitch = serializer.readFloat();
		short field = serializer.readByte();
		Location location = player.getLocation();
		if ((field & 0x01) != 0) {
			x += location.getX();
		}
		if ((field & 0x02) != 0) {
			y += location.getY();
		}
		if ((field & 0x04) != 0) {
			z += location.getX();
		}
		if ((field & 0x08) != 0) {
			yaw += location.getYaw();
		}
		if ((field & 0x10) != 0) {
			pitch += location.getPitch();
		}
		teleportConfirmId = serializer.readVarInt();
		//fake a teleport confirm packet
		if (teleportConfirmId != 0) {
			PacketCreator creator = PacketCreator.create(new PacketPlayInTeleportAccept());
			creator.writeVarInt(teleportConfirmId);
			try {
				((CraftPlayer) player).getHandle().playerConnection.a((PacketPlayInTeleportAccept) creator.create());
			} catch (CancelledPacketHandleException e) {
			} catch (Exception e) {
				SneakyThrow.sneaky(e);
			}
		}
	}

}
