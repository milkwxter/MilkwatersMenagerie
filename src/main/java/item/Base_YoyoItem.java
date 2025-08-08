package item;

import java.text.DecimalFormat;
import java.util.List;

import entity.custom.YoyoProjectileEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import sound.ModSounds;

public abstract class Base_YoyoItem extends Item {
	// variables to override
	protected double MAX_DISTANCE = 1.0;
	protected double DAMAGE = 1.0;
	
	// tooltip helper
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");
	
	// constructor
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
            
            // deal damage to the tool
            stack.hurtAndBreak(1, player, stack.getEquipmentSlot());
        }
        player.getCooldowns().addCooldown(this, 40);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    	tooltipComponents.add(Component.literal(DECIMAL_FORMAT.format(DAMAGE) + " Attack Damage")
                .withStyle(ChatFormatting.DARK_GREEN));
    	
    	tooltipComponents.add(Component.literal(DECIMAL_FORMAT.format(MAX_DISTANCE) + " Block Reach")
            .withStyle(ChatFormatting.DARK_GREEN));
    }

	public double getMaxYoyoDistance() {
		return MAX_DISTANCE;
	}
	
	public double getYoyoDamage() {
		return DAMAGE;
	}
}