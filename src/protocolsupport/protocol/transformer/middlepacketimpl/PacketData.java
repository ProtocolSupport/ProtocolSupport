package protocolsupport.protocol.transformer.middlepacketimpl;

import io.netty.util.Recycler;
import protocolsupport.protocol.PacketDataSerializer;

public class PacketData {

	private static final Recycler<PacketData> RECYCLER = new Recycler<PacketData>() {
		protected PacketData newObject(Recycler.Handle handle) {
			return new PacketData(handle);
		}
	};

	public static PacketData create(int packetId, PacketDataSerializer data) {
		PacketData packetdata = RECYCLER.get();
		packetdata.packetId = packetId;
		packetdata.data = data;
		return packetdata;
	}

	private final Recycler.Handle handle;
	private PacketData(Recycler.Handle handle) {
		this.handle = handle;
	}

	public void recycle() {
		packetId = 0;
		data = null;
		RECYCLER.recycle(this, handle);
	}

	private int packetId;
	private PacketDataSerializer data;

	public int getPacketId() {
		return packetId;
	}

	public PacketDataSerializer getData() {
		return data;
	}

}
