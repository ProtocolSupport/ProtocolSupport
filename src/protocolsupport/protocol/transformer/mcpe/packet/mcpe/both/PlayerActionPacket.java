package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.PacketCreator;

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
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_BLOCK_DIG.get());
				creator.writeByte(action);
				creator.a(new BlockPosition(x, y, z));
				creator.writeByte(1);
				return Collections.singletonList(creator.create());
			}
			case ACTION_RESPAWN: {
				//TODO: migrate to PacketCreator
				return Collections.singletonList(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			}
			case ACTION_WAKE_UP: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_ENTITY_ACTION.get());
				creator.writeVarInt(0);
				creator.writeVarInt(2);
				creator.writeVarInt(0);
				return Collections.singletonList(creator.create());
			}
			default: {
				return Collections.emptyList();
			}
		}
	}

}
