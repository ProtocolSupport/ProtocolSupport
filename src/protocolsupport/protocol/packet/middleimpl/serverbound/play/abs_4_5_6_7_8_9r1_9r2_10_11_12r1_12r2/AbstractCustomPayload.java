package protocolsupport.protocol.packet.middleimpl.serverbound.play.abs_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleNameItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePickItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public abstract class AbstractCustomPayload extends ServerBoundMiddlePacket {

	public AbstractCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected byte[] data;

	protected RecyclableCollection<ServerBoundPacketData> transformRegisterUnregister(boolean register) {
		CustomPayloadChannelsCache ccache = connection.getCache().getChannelsCache();
		String modernTag = LegacyCustomPayloadChannelName.fromPre13(tag);
		StringJoiner payloadModernTagJoiner = new StringJoiner("\u0000");
		for (String payloadLegacyTag : new String(data, StandardCharsets.UTF_8).split("\u0000")) {
			String payloadModernTag = LegacyCustomPayloadChannelName.fromPre13(payloadLegacyTag);
			if (register) {
				ccache.register(payloadModernTag, payloadLegacyTag);
			} else {
				ccache.unregister(payloadModernTag);
			}
			payloadModernTagJoiner.add(payloadModernTag);
		}
		return RecyclableSingletonList.create(MiddleCustomPayload.create(modernTag, payloadModernTagJoiner.toString().getBytes(StandardCharsets.UTF_8)));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformBookEdit() {
		String locale = connection.getCache().getAttributesCache().getLocale();
		NetworkItemStack book = ItemStackSerializer.readItemStack(Unpooled.wrappedBuffer(data), connection.getVersion(), locale, true);
		book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITTEN_BOOK));
		if (!book.isNull()) {
			return RecyclableSingletonList.create(MiddleEditBook.create(locale, book, false));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	protected RecyclableCollection<ServerBoundPacketData> transformBookSign() {
		ProtocolVersion version = connection.getVersion();
		String locale = connection.getCache().getAttributesCache().getLocale();
		NetworkItemStack book = ItemStackSerializer.readItemStack(Unpooled.wrappedBuffer(data), version, locale, true);
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITTEN_BOOK));
			if (connection.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				NBTTagCompoundWrapper nbt = book.getNBT();
				if (!nbt.isNull()) {
					if (nbt.hasKeyOfType("pages", NBTTagType.LIST)) {
						NBTTagListWrapper pages = nbt.getList("pages", NBTTagType.STRING);
						NBTTagListWrapper newPages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
						for (int i = 0; i < pages.size(); i++) {
							newPages.addString(ChatAPI.fromJSON(pages.getString(i)).toLegacyText(locale));
						}
						nbt.setList("pages", newPages);
					}
					book.setNBT(nbt);
				}
			}
			return RecyclableSingletonList.create(MiddleEditBook.create(locale, book, true));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	protected RecyclableCollection<ServerBoundPacketData> transformSetBeaconEffect() {
		ByteBuf databuf = Unpooled.wrappedBuffer(data);
		int primary = databuf.readInt();
		int secondary = databuf.readInt();
		return RecyclableSingletonList.create(MiddleSetBeaconEffect.create(primary, secondary));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformNameItem() {
		String name = StringSerializer.readString(Unpooled.wrappedBuffer(data), connection.getVersion());
		return RecyclableSingletonList.create(MiddleNameItem.create(name));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformPickItem() {
		int slot = VarNumberSerializer.readVarInt(Unpooled.wrappedBuffer(data));
		return RecyclableSingletonList.create(MiddlePickItem.create(slot));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformCustomPayload() {
		String modernName = LegacyCustomPayloadChannelName.fromPre13(tag);
		return RecyclableSingletonList.create(MiddleCustomPayload.create(modernName, data));
	}

}
