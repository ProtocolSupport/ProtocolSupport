package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTEnd;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;
import protocolsupport.protocol.utils.types.nbt.serializer.DefaultNBTSerializer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.ServerPlatform;

public class ItemStackSerializer {

	public static NetworkItemStack readItemStack(ByteBuf from, ProtocolVersion version, String locale, boolean isFromClient) {
		int type;
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
			boolean exist = from.readBoolean();
			if (!exist) {
				return NetworkItemStack.NULL;
			}
			type = VarNumberSerializer.readVarInt(from);
		} else {
			type = from.readShort();
			if (type < 0) {
				return NetworkItemStack.NULL;
			}
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
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
				to.writeBoolean(false);
			} else {
				to.writeShort(-1);
			}
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
					NBTCompound nbt = itemstack.getNBT();
					if (nbt == null) {
						nbt = new NBTCompound();
					}
					NBTCompound displayNBT = CommonNBT.getOrCreateDisplayTag(nbt);

					if (forcedDisplayName != null) {
						displayNBT.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.toJSON(forcedDisplayName)));
					}

					if (!additionalLore.isEmpty()) {
						NBTList<NBTString> loreNBT = displayNBT.getTagListOfType(CommonNBT.DISPLAY_LORE, NBTType.STRING);
						if (loreNBT == null) {
							loreNBT = new NBTList<>(NBTType.STRING);
						}
						for (String lore : additionalLore) {
							loreNBT.addTag(new NBTString(lore));
						}
						displayNBT.setTag(CommonNBT.DISPLAY_LORE, loreNBT);
					}

					itemstack.setNBT(nbt);
				}
			}

			itemstack = ItemStackRemapper.remapToClient(version, locale, itemstack);
		}
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
			to.writeBoolean(true);
			VarNumberSerializer.writeVarInt(to, itemstack.getTypeId());
		} else {
			to.writeShort(itemstack.getTypeId());
		}
		to.writeByte(itemstack.getAmount());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			to.writeShort(itemstack.getLegacyData());
		}
		writeTag(to, version, itemstack.getNBT());
	}

	public static NBTCompound readTag(ByteBuf from, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return null;
				}
				try (DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteBufInputStream(from.readSlice(length))))) {
					return DefaultNBTSerializer.INSTANCE.deserializeTag(stream);
				}
			} else if (isUsingDirectNBT(version)) {
				return DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(from));
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
			}
		} catch (EOFSignal e) {
			throw e;
		} catch (Exception e) {//TODO: only catch IO exception and specific exceptions from nbt serializer (after implementing them)
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTCompound tag) {
		if (isUsingShortLengthNBT(version)) {
			if (tag == null) {
				to.writeShort(-1);
			} else {
				MiscSerializer.writeLengthPrefixedBytes(
					to,
					(lTo, length) -> lTo.writeShort(length),
					lTo -> {
						try (DataOutputStream outputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(lTo)))) {
							DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, tag);
						} catch (Exception e) {
							throw new EncoderException(e);
						}
					}
				);
			}
		} else if (isUsingDirectNBT(version)) {
			try (ByteBufOutputStream outputstream = new ByteBufOutputStream(to)) {
				if (tag != null) {
					DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, tag);
				} else {
					DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, NBTEnd.INSTANCE);
				}
			} catch (Exception e) {
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
