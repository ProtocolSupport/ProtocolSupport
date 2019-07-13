package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BookEdit extends ServerBoundMiddlePacket {

	public BookEdit(ConnectionImpl connection) {
		super(connection);
	}

	protected byte type;
	protected byte slot;
	protected byte pagenum;
	protected byte pagenum2;
	protected String text;
	protected String photo;
	protected String title;
	protected String author;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		ProtocolVersion version = connection.getVersion();
		type = clientdata.readByte();
		slot = clientdata.readByte();
		switch (type) {
			case TYPE_REPLACE: {
				pagenum = clientdata.readByte();
				text = StringSerializer.readString(clientdata, version);
				photo = StringSerializer.readString(clientdata, version);
				break;
			}
			case TYPE_ADD: {
				pagenum = clientdata.readByte();
				text = StringSerializer.readString(clientdata, version);
				photo = StringSerializer.readString(clientdata, version);
				break;
			}
			case TYPE_DELETE: {
				pagenum = clientdata.readByte();
				break;
			}
			case TYPE_SWAP: {
				pagenum = clientdata.readByte();
				pagenum2 = clientdata.readByte();
				break;
			}
			case TYPE_SIGN: {
				title = StringSerializer.readString(clientdata, version);
				author = StringSerializer.readString(clientdata, version);
				break;
			}
		}
	}

	private static final int TYPE_REPLACE = 0;
	private static final int TYPE_ADD = 1;
	private static final int TYPE_DELETE = 2;
	private static final int TYPE_SWAP = 3;
	private static final int TYPE_SIGN = 4;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		NetworkItemStack bookItem = cache.getPEInventoryCache().getItemInHand();
		if (!bookItem.isNull() && MaterialAPI.getItemByNetworkId(bookItem.getTypeId()) == Material.WRITABLE_BOOK) {
			NBTCompound bookNBT = bookItem.getNBT() != null ? bookItem.getNBT() : new NBTCompound();
			NBTList<NBTString> pages = bookNBT.getTagListOfType("pages", NBTType.STRING);
			int maxpage = ((pagenum + 1) > pages.size()) ? (pagenum + 1) : (pages.size());
			//We can't set the text at an index, so we have to reiterate and construct a new pages list for each action.
			NBTList<NBTString> newpages = new NBTList<>(NBTType.STRING);
			switch (type) {
				case TYPE_REPLACE: {
					//Cut the crap. No text changed? No packet.
					if ((pages.isEmpty() && text == "") || (pages.getTag(pagenum).getValue().equals(text))) {
						return RecyclableEmptyList.get();
					}
					for (int i = 0; i < maxpage; i++) {
						if (i == pagenum) {
							newpages.addTag(new NBTString(text));
						} else {
							if (i >= pages.size()) {
								newpages.addTag(new NBTString(""));
							} else {
								newpages.addTag(pages.getTag(i));
							}
						}
					}
					bookNBT.setTag("pages", newpages);
					bookItem.setNBT(bookNBT);
					return RecyclableSingletonList.create(editBook(bookItem));
				}
				case TYPE_ADD: {
					//Adding a page, means shifting too all the other pages, unless the page isn't there yet.
					for (int i = 0; i < maxpage; i++) {
						if (i == pagenum) {
							newpages.addTag(new NBTString(text));
						} else if (i >= pages.size()) {
							newpages.addTag(new NBTString(""));
						}
						if (i < pages.size()) {
							newpages.addTag(pages.getTag(i));
						}
					}
					bookNBT.setTag("pages", newpages);
					bookItem.setNBT(bookNBT);
					return RecyclableSingletonList.create(editBook(bookItem));
				}
				case TYPE_DELETE: {
					//Deleting a page means sending everything except that page.
					for (int i = 0; i < pages.size(); i++) {
						if (i != pagenum) {
							newpages.addTag(pages.getTag(i));
						}
					}
					bookNBT.setTag("pages", newpages);
					bookItem.setNBT(bookNBT);
					return RecyclableSingletonList.create(editBook(bookItem));
				}
				case TYPE_SWAP: {
					//Swaping a page means sending everything, but two pages are swapped.
					for (int i = 0; i < pages.size(); i++) {
						if (i == pagenum) {
							newpages.addTag(pages.getTag(pagenum2));
						} else if (i == pagenum2) {
							newpages.addTag(pages.getTag(pagenum));
						} else {
							newpages.addTag(pages.getTag(i));
						}
					}
					bookNBT.setTag("pages", newpages);
					bookItem.setNBT(bookNBT);
					return RecyclableSingletonList.create(editBook(bookItem));
				}
				case TYPE_SIGN: {
					bookNBT.setTag("author", new NBTString(author));
					bookNBT.setTag("title", new NBTString(title));
					bookNBT.setTag("pages", pages);
					bookItem.setNBT(bookNBT);
					return RecyclableSingletonList.create(signBook(bookItem));
				}
			}
		}
		return RecyclableEmptyList.get();
	}

	private static ServerBoundPacketData editBook(NetworkItemStack book) {
		ByteBuf payload = Unpooled.buffer();
		ItemStackSerializer.writeItemStack(payload, book);
		return MiddleCustomPayload.create("minecraft:BEdit", MiscSerializer.readAllBytes(payload));
	}

	private static ServerBoundPacketData signBook(NetworkItemStack book) {
		ByteBuf payload = Unpooled.buffer();
		ItemStackSerializer.writeItemStack(payload, book);
		return MiddleCustomPayload.create("minecraft:BSign", MiscSerializer.readAllBytes(payload));
	}

}
