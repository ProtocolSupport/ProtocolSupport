package protocolsupport.protocol.types.particle;

import java.util.Arrays;

import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.particle.types.ParticleBlock;
import protocolsupport.protocol.types.particle.types.ParticleDust;
import protocolsupport.protocol.types.particle.types.ParticleFallingDust;
import protocolsupport.protocol.types.particle.types.ParticleItem;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.TypeSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;

public class ParticleDataSerializer extends TypeSerializer<Particle> {

	public static final ParticleDataSerializer INSTANCE = new ParticleDataSerializer();

	protected ParticleDataSerializer() {
		register(Particle.class, (to, particle) -> {}, ProtocolVersionsHelper.UP_1_13);
		register(ParticleDust.class, (to, particle) -> {
			to.writeFloat(particle.getRed());
			to.writeFloat(particle.getGreen());
			to.writeFloat(particle.getBlue());
			to.writeFloat(particle.getScale());
		}, ProtocolVersionsHelper.UP_1_13);
		Arrays.stream(ProtocolVersionsHelper.UP_1_13)
		.forEach(version -> {
			FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
			register(ParticleBlock.class, (to, particle) -> VarNumberSerializer.writeVarInt(to, flatteningBlockDataTable.getId(particle.getBlockData())), version);
			register(ParticleFallingDust.class, (to, particle) -> VarNumberSerializer.writeFixedSizeVarInt(to, flatteningBlockDataTable.getId(particle.getBlockData())), version);
			register(ParticleItem.class, (to, particle) -> ItemStackSerializer.writeItemStack(to, version, ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack())), version);
		});
	}

}
