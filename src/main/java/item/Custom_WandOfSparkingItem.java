package item;

import entity.custom.WandOfSparkingProjectileEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sound.ModSounds;

public class Custom_WandOfSparkingItem extends Base_MagicItem{

	public Custom_WandOfSparkingItem(Properties properties) {
		super(properties);
		this.MANA_COST = 4.0;
	}
	
	@Override
    protected void onMagicUse(Level level, Player player, InteractionHand hand, ItemStack stack) {
        level.playSound(null, player.blockPosition(), ModSounds.MAGIC_CAST.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        
        if (!level.isClientSide) {
            WandOfSparkingProjectileEntity projectile = new WandOfSparkingProjectileEntity(level, player);
            projectile.setOwner(player);
            level.addFreshEntity(projectile);
        }
    }
}
