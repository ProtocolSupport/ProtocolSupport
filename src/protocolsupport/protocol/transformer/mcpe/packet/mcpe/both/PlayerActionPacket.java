package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;

public class PlayerActionPacket implements DualPEPacket {

	private final static int ACTION_START_BREAK = 0;
	private final static int ACTION_CANCEL_BREAK = 1;
	private final static int ACTION_CONSUME_ITEM = 5;
	private final static int ACTION_WAKE_UP = 6;
	private final static int ACTION_RESPAWN = 7;

	protected long id;
	protected int action;
	protected int x;
	protected int y;
	protected int z;
	protected int face;

	@Override
	public int getId() {
		return PEPacketIDs.PLAYER_ACTION_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		id = buf.readLong();
		action = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		face = buf.readInt();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		switch (action) {
			case ACTION_START_BREAK:
			case ACTION_CANCEL_BREAK:
			case ACTION_CONSUME_ITEM: {
				PacketPlayInBlockDig dig = new PacketPlayInBlockDig();
				PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer());
				try {
					packetdata.writeByte(action);
					packetdata.a(new BlockPosition(x, y, z));
					packetdata.writeByte(1);
					dig.a(new PacketDataSerializer(packetdata));
				} finally {
					packetdata.release();
				}
				return Collections.singletonList(dig);
			}
			case ACTION_RESPAWN: {
				return Collections.singletonList(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			}
			case ACTION_WAKE_UP: {
				PacketPlayInEntityAction action = new PacketPlayInEntityAction();
				PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer());
				try {
					packetdata.b(0);
					packetdata.b(2);
					packetdata.b(0);
					action.a(packetdata);
				} finally {
					packetdata.release();
				}
				return Collections.singletonList(action);
			}
			default: {
				return Collections.emptyList();
			}
		}
	}

}
