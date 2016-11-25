package protocolsupport.utils.nms;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.LocaleI18n;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;

public class NMSUtils {

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

	public static Object createLoginDisconnectPacket(String message) {
		return new PacketLoginOutDisconnect(new ChatComponentText(message));
	}

	public static Object createPlayDisconnectPacket(String message) {
		return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
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

}
