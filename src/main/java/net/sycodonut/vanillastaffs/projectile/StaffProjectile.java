package net.sycodonut.vanillastaffs.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.sycodonut.vanillastaffs.VanillaStaffItems;
import net.sycodonut.vanillastaffs.VanillaStaffs;
import net.sycodonut.vanillastaffs.custom.StaffItem;

public class StaffProjectile extends ThrownItemEntity {
    private String staffType;

    public StaffProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public StaffProjectile(World world, LivingEntity owner, String staffType) {
        super(VanillaStaffsProjectiles.StaffProjectileEntityType, owner, world);
        this.staffType = staffType;
    }

    public StaffProjectile(World world, double x, double y, double z) {
        super(VanillaStaffsProjectiles.StaffProjectileEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        //temp
        return VanillaStaffItems.frost_staff;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        //Frost Staff
        if (entity instanceof LivingEntity livingEntity && staffType.equals("frostStaff")) {
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 1))); // Applies Slowness
            livingEntity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 2F, 1F);
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3);
            this.kill(); // kills the projectile
        }

    }
}
