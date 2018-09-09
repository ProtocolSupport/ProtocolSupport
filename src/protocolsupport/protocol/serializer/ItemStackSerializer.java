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
		int type = 0;
		if (version == ProtocolVersion.MINECRAFT_PE) {
			type = VarNumberSerializer.readSVarInt(from);
		} else {
			type = from.readShort();
		}
		if ((type < 0) || ((type == 0) && (version == ProtocolVersion.MINECRAFT_PE))) {
			//Non or empty item stacks can also be 0 in PE.
			return NetworkItemStack.NULL;
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(type);
		if (version == ProtocolVersion.MINECRAFT_PE) {
			int amountdata = VarNumberSerializer.readSVarInt(from);
			itemstack.setAmount(amountdata & 0x7F);
			itemstack.setLegacyData((amountdata >> 8) & 0xFFFF);
		} else {
			itemstack.setAmount(from.readByte());
		}
		if ((version.getProtocolType() == ProtocolType.PC) && version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setLegacyData(from.readUnsignedShort());
		}
		if (version == ProtocolVersion.MINECRAFT_PE) {
			itemstack.setNBT(readTag(from, false, version));
			//TODO: Read the rest properly..
			from.readByte(); //TODO: CanPlaceOn PE
			from.readByte(); //TODO: CanDestroy PE
		} else {
			itemstack.setNBT(readTag(from, version));
		}
		if (isFromClient) {
			itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack);
		}
		return itemstack;
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, NetworkItemStack itemstack, boolean isToClient) {
		if ((itemstack == null) || itemstack.isNull()) {
			if (version == ProtocolVersion.MINECRAFT_PE) {
				VarNumberSerializer.writeVarInt(to, 0);
			} else {
				to.writeShort(-1);
			}
			return;
		}
		NetworkItemStack witemstack = itemstack;
		if (isToClient) {
			if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
				ItemStack bukkitStack = ServerPlatform.get().getMiscUtils().createItemStackFromNetwork(itemstack);
				ItemStackWriteEvent event = new ItemStackWriteEvent(version, locale, bukkitStack);
				Bukkit.getPluginManager().callEvent(event);
				List<String> additionalLore = event.getAdditionalLore();
				BaseComponent forcedDisplayName = event.getForcedDisplayName();
				if ((forcedDisplayName != null) || !additionalLore.isEmpty()) {
					NBTTagCompoundWrapper nbt = witemstack.getNBT();
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
		if (version == ProtocolVersion.MINECRAFT_PE) {
			VarNumberSerializer.writeSVarInt(to, witemstack.getTypeId());
			VarNumberSerializer.writeSVarInt(to, ((witemstack.getLegacyData() & 0xFFFF) << 8) | witemstack.getAmount());
			writeTag(to, false, version, witemstack.getNBT());
			to.writeByte(0); //TODO: CanPlaceOn PE
			to.writeByte(0); //TODO: CanDestroy PE
		} else {
			to.writeShort(witemstack.getTypeId());
			to.writeByte(witemstack.getAmount());
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
				to.writeShort(witemstack.getLegacyData());
			}
			writeTag(to, version, witemstack.getNBT());
		}
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
		return readTag(from, false, version);
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, boolean varint, ProtocolVersion version) {
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
			} else if (isUsingPENBT(version)) {
				if (!varint) { // VarInts NBTs doesn't have length
					final short length = from.readShortLE();
					if (length <= 0) {
						return NBTTagCompoundWrapper.NULL;
					}
				}
				return NBTTagCompoundSerializer.readPeTag(from, varint);
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTTagCompoundWrapper tag) {
		writeTag(to, false, version, tag);
	}

	public static void writeTag(ByteBuf to, boolean varint, ProtocolVersion version, NBTTagCompoundWrapper tag) {
		try {
			if (isUsingShortLengthNBT(version)) {
				if (tag.isNull()) {
					to.writeShort(-1);
				} else {
					int writerIndex = to.writerIndex();
					//fake length
					to.writeShort(0);
					//actual nbt
					try (DataOutputStream outputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(to)))) {
						NBTTagCompoundSerializer.writeTag(outputstream, tag);
					}
					//now replace fake length with real length
					to.setShort(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else if (isUsingDirectNBT(version)) {
				NBTTagCompoundSerializer.writeTag(new ByteBufOutputStream(to), tag);
			} else if (isUsingPENBT(version)) {
				if (tag.isNull()) {
					to.writeShortLE(0);
				} else {
					int writerIndex = to.writerIndex();
					//fake length
					if (!varint) { // VarInt NBTs doesn't have length
						to.writeShortLE(0);
					}
					//actual nbt
					NBTTagCompoundSerializer.writePeTag(to, varint, tag);
					//now replace fake length with real length
					if (!varint) {
						to.setShortLE(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
					}
				}
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to write nbt of version {0}", version));
			}
		} catch (Throwable ioexception) {
			throw new EncoderException(ioexception);
		}
	}

	private static final boolean isUsingShortLengthNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10);
	}

	private static final boolean isUsingDirectNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

	private static final boolean isUsingPENBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PE) && (version == ProtocolVersion.MINECRAFT_PE);
	}

}
