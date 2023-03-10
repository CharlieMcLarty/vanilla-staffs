package net.sycodonut.vanillastaffs;

import net.fabricmc.api.ModInitializer;

import net.sycodonut.vanillastaffs.enchantment.VanillaStaffsEnchantments;
import net.sycodonut.vanillastaffs.projectile.VanillaStaffsProjectiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaStaffs implements ModInitializer {
	public static final String MOD_ID = "vanillastaffs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//	public static final EntityType<StaffProjectile> STAFF_PROJECTILE_ENTITY_TYPE = Registry.register(
//			Registries.ENTITY_TYPE,
//			new Identifier(MOD_ID, "staff_projectile"),
//			FabricEntityTypeBuilder.<StaffProjectile>create(SpawnGroup.MISC, StaffProjectile::new)
//					.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
//					.trackRangeBlocks(4).trackedUpdateRate(10)
//					.build()
//	);

	@Override
	public void onInitialize() {
		VanillaStaffItems.registerModItems();
		VanillaStaffsEnchantments.registerModEnchantments();
		VanillaStaffsProjectiles.registerModProjectiles();
		//EntityRendererRegistry.register(VanillaStaffsProjectiles.StaffProjectile, FlyingItemEntityRenderer::new);
	}
}
