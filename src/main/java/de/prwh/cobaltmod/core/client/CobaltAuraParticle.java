package de.prwh.cobaltmod.core.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class CobaltAuraParticle extends Particle {

	public CobaltAuraParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		float f = 0.0F;
		float f2 = 0.4F;
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f2;
		this.setParticleTextureIndex(0);
		this.setSize(0.02F, 0.02F);
		this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
		this.motionX *= 0.019999999552965164D;
		this.motionY *= 0.019999999552965164D;
		this.motionZ *= 0.019999999552965164D;
		this.particleMaxAge = (int) (20.0D / (Math.random() * 0.8D + 0.2D));
		this.canCollide = false;
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;

		if (this.particleMaxAge-- <= 0) {
			this.setExpired();
		}
	}
}
