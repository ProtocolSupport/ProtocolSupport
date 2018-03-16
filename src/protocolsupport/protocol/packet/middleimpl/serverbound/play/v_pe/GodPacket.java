package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class GodPacket extends ServerBoundMiddlePacket {

	protected static final int ACTION_NORMAL = 0;
	protected static final int ACTION_MISMATCH = 1;
	protected static final int ACTION_USE_ITEM = 2;
	protected static final int ACTION_USE_ENTITY = 3;
	protected static final int ACTION_RELEASE_ITEM = 4;

	protected UseItem useItemMiddlePacket = new UseItem();
	protected UseEntity useEntityMiddlePacket = new UseEntity();
	protected ReleaseItem releaseItemMiddlePacket = new ReleaseItem();

	@Override
	public void setConnection(Connection connection) {
		super.setConnection(connection);
		useItemMiddlePacket.setConnection(connection);
		useEntityMiddlePacket.setConnection(connection);
		releaseItemMiddlePacket.setConnection(connection);
	}

	@Override
	public void setSharedStorage(NetworkDataCache sharedstorage) {
		super.setSharedStorage(sharedstorage);
		useItemMiddlePacket.setSharedStorage(sharedstorage);
		useEntityMiddlePacket.setSharedStorage(sharedstorage);
		releaseItemMiddlePacket.setSharedStorage(sharedstorage);
	}

	protected int actionId;
	protected InvTransaction[] transactions;
	protected ServerBoundMiddlePacket simpleActionMiddlePacket;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		actionId = VarNumberSerializer.readVarInt(clientdata);

		transactions = new InvTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for (int i = 0; i < transactions.length; i++) {
			transactions[i] = InvTransaction.readFromStream(clientdata, cache.getAttributesCache().getLocale(), connection.getVersion());
		}

		switch (actionId) {
			case ACTION_USE_ITEM: {
				simpleActionMiddlePacket = useItemMiddlePacket;
				break;
			}
			case ACTION_USE_ENTITY: {
				simpleActionMiddlePacket = useEntityMiddlePacket;
				break;
			}
			case ACTION_RELEASE_ITEM: {
				simpleActionMiddlePacket = releaseItemMiddlePacket;
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				simpleActionMiddlePacket = null;
				break;
			}
		}

		if (simpleActionMiddlePacket != null) {
			simpleActionMiddlePacket.readFromClientData(clientdata);
		}

		clientdata.skipBytes(clientdata.readableBytes());
	}

	protected static final int SOURCE_CONTAINER = 0;
	protected static final int SOURCE_GLOBAL = 1;
	protected static final int SOURCE_WORLD_INTERACTION = 2;
	protected static final int SOURCE_CREATIVE = 3;
	protected static final int SOURCE_TODO = 99999;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (simpleActionMiddlePacket != null) {
			simpleActionMiddlePacket.toNative();
		}
		return RecyclableEmptyList.get();
	}

	protected static class InvTransaction {

		protected int sourceId;

		private static InvTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InvTransaction transaction = new InvTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch (transaction.sourceId) {
				case SOURCE_CONTAINER: {
					VarNumberSerializer.readSVarInt(from);
					break;
				}
				case SOURCE_WORLD_INTERACTION: {
					VarNumberSerializer.readVarInt(from);
					break;
				}
				case SOURCE_TODO: {
					VarNumberSerializer.readSVarInt(from);
					break;
				}
				case SOURCE_GLOBAL:
				case SOURCE_CREATIVE:
				default: {
					break;
				}
			}
			VarNumberSerializer.readVarInt(from);
			ItemStackSerializer.readItemStack(from, version, locale, true);
			ItemStackSerializer.readItemStack(from, version, locale, true);
			return transaction;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
