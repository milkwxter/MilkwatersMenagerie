package item;

import entity.custom.YoyoProjectileEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sound.ModSounds;

public abstract class Base_YoyoItem extends Item {
	protected static final double MAX_DISTANCE = 5.0;
	
    public Base_YoyoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    	ItemStack stack = player.getItemInHand(hand);
    	
        if (!level.isClientSide) {
        	// spawn the yoyo
        	YoyoProjectileEntity projectile = new YoyoProjectileEntity(level, player, stack);
            projectile.setPos(player.getX(), player.getEyeY(), player.getZ());
            level.addFreshEntity(projectile);
            
            // extra flair
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.GENERIC_ITEM_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        player.getCooldowns().addCooldown(this, 40);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

	public double getMaxYoyoDistance() {
		return MAX_DISTANCE;
	}
}