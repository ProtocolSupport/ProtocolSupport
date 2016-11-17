package protocolsupport.utils;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.SpigotConfig;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.LocaleI18n;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;

public class ServerPlatformUtils {

	public static boolean checkServerSupported() {
		try {
			NetworkManager.a.getName();
			SpigotConfig.config.contains("test");
			return true;
		} catch (NoClassDefFoundError e) {
		}
		return false;
	}

	public static Object createInboundInventoryClosePacket() {
		return new PacketPlayInCloseWindow();
	}

	public static Object createOutboundChatPacket(String message, int position) {
		return new PacketPlayOutChat(ChatSerializer.a(message), (byte) position);
	}

	private static final BaseComponent empty = new TextComponent("");
	public static Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		serializer.a(ChatAPI.toJSON(header != null ? header : empty));
		serializer.a(ChatAPI.toJSON(footer != null ? footer : empty));
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	public static Object createTitleResetPacket() {
		 return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
	}

	public static Object createTitleClearPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
	}

	public static Object createTitleMainPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
	}

	public static Object createTitleSubPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
	}

	public static Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
	}

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

	public static String localize(String key, Object... args) {
		return LocaleI18n.a(key, args);
	}

	public static ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		return CraftItemStack.asCraftMirror(new net.minecraft.server.v1_11_R1.ItemStack(tag.unwrap()));
	}

	public static NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		net.minecraft.server.v1_11_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound compound = new NBTTagCompound();
		nmsitemstack.save(compound);
		return NBTTagCompoundWrapper.wrap(compound);
	}

	public static Integer getItemIdByName(String registryname) {
		Item item = Item.b(registryname);
		if (item != null) {
			return Item.getId(item);
		}
		return null;
	}

	public static boolean isDebugging() {
		return getServer().isDebugging();
	}

	private static final String DEBUG_PROPERTY = "debug";

	public static void enableDebug() {
		getServer().getPropertyManager().setProperty(DEBUG_PROPERTY, Boolean.TRUE);
	}

	public static void disableDebug() {
		getServer().getPropertyManager().setProperty(DEBUG_PROPERTY, Boolean.FALSE);
	}

}
