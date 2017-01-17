package protocolsupport.zplatform;

import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;
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
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MiscImplUtils {

	public static Object createInboundInventoryClosePacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayInCloseWindow();
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createOutboundChatPacket(String message, int position) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutChat(ChatSerializer.a(message), (byte) position);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	private static final BaseComponent empty = new TextComponent("");

	public static Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
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
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleResetPacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleClearPacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleMainPacket(String title) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleSubPacket(String title) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createLoginDisconnectPacket(String message) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketLoginOutDisconnect(new ChatComponentText(message));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createPlayDisconnectPacket(String message) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static String localize(String key, Object... args) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return LocaleI18n.a(key, args);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return CraftItemStack.asCraftMirror(new net.minecraft.server.v1_11_R1.ItemStack(((SpigotNBTTagCompoundWrapper) tag).unwrap()));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				net.minecraft.server.v1_11_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
				NBTTagCompound compound = new NBTTagCompound();
				nmsitemstack.save(compound);
				return SpigotNBTTagCompoundWrapper.wrap(compound);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Integer getItemIdByName(String registryname) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				Item item = Item.b(registryname);
				if (item != null) {
					return Item.getId(item);
				}
				return null;
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

}
