package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.NBTTagCompoundSerializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class ItemStackSerializer {

	public static NetworkItemStack readItemStack(ByteBuf from, ProtocolVersion version, String locale, boolean isFromClient) {
		int type = from.readShort();
		if (type < 0) {
			return NetworkItemStack.NULL;
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(type);
		itemstack.setAmount(from.readByte());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setLegacyData(from.readUnsignedShort());
		}
		itemstack.setNBT(readTag(from, version));
		if (isFromClient) {
			itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack);
		}
		return itemstack;
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, NetworkItemStack itemstack, boolean isToClient) {
		if (itemstack.isNull()) {
			to.writeShort(-1);
			return;
		}

		if (isToClient) {
			if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
				ItemStack bukkitStack = ServerPlatform.get().getMiscUtils().createItemStackFromNetwork(itemstack);
				ItemStackWriteEvent event = new ItemStackWriteEvent(version, locale, bukkitStack);
				Bukkit.getPluginManager().callEvent(event);
				List<String> additionalLore = event.getAdditionalLore();
				BaseComponent forcedDisplayName = event.getForcedDisplayName();
				if ((forcedDisplayName != null) || !additionalLore.isEmpty()) {
					NBTTagCompoundWrapper nbt = itemstack.getNBT();
					if (nbt.isNull()) {
						nbt = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					}
					NBTTagCompoundWrapper displayNBT = CommonNBT.getOrCreateDisplayTag(nbt);

					if (forcedDisplayName != null) {
						displayNBT.setString(CommonNBT.DISPLAY_NAME, ChatAPI.toJSON(forcedDisplayName));
					}

					if (!additionalLore.isEmpty()) {
						NBTTagListWrapper loreNBT = displayNBT.getList(CommonNBT.DISPLAY_LORE, NBTTagType.STRING);
						additionalLore.forEach(loreNBT::addString);
						displayNBT.setList(CommonNBT.DISPLAY_LORE, loreNBT);
					}

					itemstack.setNBT(nbt);
				}
			}

			itemstack = ItemStackRemapper.remapToClient(version, locale, itemstack);
		}

		to.writeShort(itemstack.getTypeId());
		to.writeByte(itemstack.getAmount());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			to.writeShort(itemstack.getLegacyData());
		}
		writeTag(to, version, itemstack.getNBT());
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return NBTTagCompoundWrapper.NULL;
				}
				try (InputStream inputstream = new GZIPInputStream(new ByteBufInputStream(from.readSlice(length)))) {
					return NBTTagCompoundSerializer.readTag(new DataInputStream(inputstream));
				}
			} else if (isUsingDirectNBT(version)) {
				return NBTTagCompoundSerializer.readTag(new ByteBufInputStream(from));
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTTagCompoundWrapper tag) {
		if (isUsingShortLengthNBT(version)) {
			if (tag.isNull()) {
				to.writeShort(-1);
			} else {
				MiscSerializer.writeLengthPrefixedBytes(
					to,
					(lTo, length) -> lTo.writeShort(length),
					lTo -> {
						try (DataOutputStream outputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(lTo)))) {
							NBTTagCompoundSerializer.writeTag(outputstream, tag);
						} catch (IOException e) {
							throw new EncoderException(e);
						}
					}
				);
			}
		} else if (isUsingDirectNBT(version)) {
			try (ByteBufOutputStream outputstream = new ByteBufOutputStream(to)) {
				NBTTagCompoundSerializer.writeTag(outputstream, tag);
			} catch (IOException e) {
				throw new EncoderException(e);
			}
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Dont know how to write nbt of version {0}", version));
		}
	}

	private static final boolean isUsingShortLengthNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10);
	}

	private static final boolean isUsingDirectNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
