package protocolsupport.utils;

import io.netty.channel.Channel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityTrackerEntry;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.WorldServer;

public class Utils {

	@SuppressWarnings("serial")
	private static final HashMap<String, Byte> inventoryNameToId = new HashMap<String, Byte>() {
		{
			put("minecraft:chest", (byte) 0);
			put("minecraft:crafting_table", (byte) 1);
			put("minecraft:furnace", (byte) 2);
			put("minecraft:dispenser", (byte) 3);
			put("minecraft:enchanting_table", (byte) 4);
			put("minecraft:brewing_stand", (byte) 5);
			put("minecraft:villager", (byte) 6);
			put("minecraft:beacon", (byte) 7);
			put("minecraft:anvil", (byte) 8);
			put("minecraft:hopper", (byte) 9);
			put("minecraft:dropper", (byte) 10);
			put("EntityHorse", (byte) 11);
		}
	};

	public static byte getInventoryId(String inventoryid) {
		return inventoryNameToId.get(inventoryid);
	}

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static Entity getEntity(Channel channel, int entityId) {
		WorldServer world = (WorldServer) ((CraftPlayer) DataStorage.getPlayer(channel.remoteAddress())).getHandle().getWorld();
		//try direct map lookup
		Entity entity = world.a(entityId);
		//search entity tracker
		if (entity == null) {
			EntityTrackerEntry entry = (EntityTrackerEntry) world.tracker.trackedEntities.d(entityId);
			if (entry != null) {
				entity = entry.tracker;
			}
		}
		//last chance, search it entity list (really slow)
		if (entity == null) {
			for (Object lentityObj : world.entityList) {
				Entity lentity = (Entity) lentityObj;
				if (lentity.getId() == entityId) {
					entity = lentity;
					break;
				}
			}
		}
		return entity;
	}

	public static void writeTheRestOfTheData(PacketDataSerializer input, PacketDataSerializer output) {
		output.writeBytes(input.readBytes(input.readableBytes()));
	}

	@SuppressWarnings("unchecked")
	public static <T> T setAccessible(AccessibleObject object) {
		object.setAccessible(true);
		return (T) object;
	}

	public static Channel getChannel(NetworkManager nm) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return (Channel) Utils.<Field>setAccessible(NetworkManager.class.getDeclaredField("i")).get(nm);
	}

}
