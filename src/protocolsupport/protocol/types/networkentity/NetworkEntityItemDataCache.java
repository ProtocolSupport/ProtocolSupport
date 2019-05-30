package protocolsupport.protocol.types.networkentity;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class NetworkEntityItemDataCache extends NetworkEntityDataCache {

	protected boolean hasData = false;
	protected double x;
	protected double y;
	protected double z;
	protected float motX;
	protected float motY;
	protected float motZ;
	protected NetworkItemStack itemstack;
	protected boolean spawned = false;

	public void setData(double x, double y, double z, float motX, float motY, float motZ) {
		this.hasData = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
	}

	public RecyclableCollection<ClientBoundPacketData> updateItem(ProtocolVersion version, int entityId, NetworkItemStack itemstack) {
		if (!hasData) {
			return RecyclableEmptyList.get();
		}
		RecyclableArrayList<ClientBoundPacketData> updatepackets = RecyclableArrayList.create();
		if ((this.itemstack == null) || !this.itemstack.equals(itemstack)) {
			if (spawned) {
				updatepackets.add(EntityDestroy.create(entityId));
				spawned = false;
			}
			this.itemstack = itemstack;
		}
		if (!spawned) {
			ClientBoundPacketData spawn = ClientBoundPacketData.create(PEPacketIDs.ADD_ITEM_ENTITY);
			VarNumberSerializer.writeSVarLong(spawn, entityId);
			VarNumberSerializer.writeVarLong(spawn, entityId);
			ItemStackSerializer.writeItemStack(spawn, version, I18NData.DEFAULT_LOCALE, itemstack);
			spawn.writeFloatLE((float) x);
			spawn.writeFloatLE((float) y);
			spawn.writeFloatLE((float) z);
			spawn.writeFloatLE(motX / 8000);
			spawn.writeFloatLE(motY / 8000);
			spawn.writeFloatLE(motZ / 8000);
			VarNumberSerializer.writeVarInt(spawn, 0); // Metadata?
			spawn.writeBoolean(false); //From fishing. Why do we care?
			updatepackets.add(spawn);
			spawned = true;
		}
		return updatepackets;
	}

}
