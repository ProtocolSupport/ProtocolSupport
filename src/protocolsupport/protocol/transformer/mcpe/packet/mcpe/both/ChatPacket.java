package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;

public class ChatPacket implements DualPEPacket {

	protected TextType type;
	protected String source;
	protected String message;
	protected String[] params;

	public ChatPacket() {
	}

	public ChatPacket(String message) {
		this.type = TextType.RAW;
		this.message = message + "  "; //add two spaces to make it show normally (dafuq?)
	}

	@Override
	public int getId() {
		return PEPacketIDs.TEXT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		type = TextType.fromNum(serializer.readByte());
		serializer.readString(); // Nickname
		switch (type) {
			case CHAT: {
				source = serializer.readString();
				break;
			}
			case POPUP:
			case RAW:
			case TIP: {
				message = serializer.readString();
				break;
			}
			case TRANSLATION: {
				message = serializer.readString();
				int count = serializer.readByte();
				params = new String[count];
				for (int i = 0; i < count; i++) {
					params[i] = serializer.readString();
				}
				break;
			}
		}
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		buf.writeByte(type.getType());
		switch (type) {
			case CHAT: {
				serializer.writeString(source);
				break;
			}
			case RAW:
			case POPUP:
			case TIP: {
				serializer.writeString(message);
				break;
			}
			case TRANSLATION: {
				serializer.writeString(message);
				serializer.writeByte(params.length);
				for (String param : params) {
					serializer.writeString(param);
				}
				break;
			}
		}
		return this;
	}

	@Override
	public List<PacketPlayInChat> transfrom() {
		return Collections.singletonList(new PacketPlayInChat(source));
	}


    private static enum TextType {

        RAW(0),
        CHAT(1),
        TRANSLATION(2),
        POPUP(3),
        TIP(4);

        private int type;

        TextType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public static TextType fromNum(int num) {
            switch (num) {
                case 0: {
                    return RAW;
                }
                case 1: {
                    return CHAT;
                }
                case 2: {
                    return TRANSLATION;
                }
                case 3: {
                    return POPUP;
                }
                case 4: {
                    return TIP;
                }
                default: {
                    return RAW;
                }
            }
        }
    }

}
