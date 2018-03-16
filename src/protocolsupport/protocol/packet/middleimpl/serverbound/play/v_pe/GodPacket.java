package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class GodPacket extends ServerBoundMiddlePacket {

	//Transactions
	protected InfTransaction[] transactions;
	//Wrapped packet
	protected int actionId;
	protected ServerBoundMiddlePacket packet;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		actionId = VarNumberSerializer.readVarInt(clientdata);

		transactions = new InfTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < transactions.length; i++) {
			transactions[i] = InfTransaction.readFromStream(clientdata, cache.getAttributesCache().getLocale(), connection.getVersion());
		}

		switch(actionId) {
			case ACTION_USE_ITEM: {
				packet = new UseItem();
				break;
			}
			case ACTION_USE_ENTITY: {
				packet = new UseEntity();
				break;
			}
			case ACTION_RELEASE_ITEM: {
				packet = new ReleaseItem();
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				packet = null;
				break;
			}
		}

		if (packet != null) {
			packet.setSharedStorage(cache);
			packet.setConnection(connection);
			packet.readFromClientData(clientdata);
		}

		clientdata.readBytes(clientdata.readableBytes());
	}

	//Sources
	public static final int SOURCE_CONTAINER = 0;
	public static final int SOURCE_GLOBAL = 1;
	public static final int SOURCE_WORLD_INTERACTION = 2;
	public static final int SOURCE_CREATIVE = 3;
	public static final int SOURCE_TODO = 99999;
	//Actions
	public static final int ACTION_NORMAL = 0;
	public static final int ACTION_MISMATCH = 1;
	public static final int ACTION_USE_ITEM = 2;
	public static final int ACTION_USE_ENTITY = 3;
	public static final int ACTION_RELEASE_ITEM = 4;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (packet != null) {
			packet.toNative();
		}
		return RecyclableEmptyList.get();
	}

	public static class InfTransaction {

		private int sourceId;

		private static InfTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InfTransaction transaction = new InfTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch(transaction.sourceId) {
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
