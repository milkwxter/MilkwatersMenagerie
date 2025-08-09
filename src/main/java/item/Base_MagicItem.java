package item;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import player.ManaHelper;

public class Base_MagicItem extends Item {
	// variables to override
	protected double MANA_COST = 1.0;
	
	// tooltip helper
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");

	public Base_MagicItem(Properties properties) {
		super(properties);
	}

	@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        int cost = (int) Math.ceil(MANA_COST);

        if (!level.isClientSide) {
        	int currentMana = ManaHelper.getMana(player);
        	// if he has enough mana
        	if (currentMana >= cost) {
        		ManaHelper.consumeMana(player, cost);
                onMagicUse(level, player, hand, player.getItemInHand(hand));
                player.getCooldowns().addCooldown(this, 26);
                return InteractionResultHolder.success(player.getItemInHand(hand));
        	}
        	// not enough mana
        	else {
        		return InteractionResultHolder.fail(player.getItemInHand(hand));
        	}
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    protected void onMagicUse(Level level, Player player, InteractionHand hand, ItemStack stack) {
        // override me
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    	tooltipComponents.add(Component.literal(DECIMAL_FORMAT.format(MANA_COST) + " Mana Cost")
                .withStyle(ChatFormatting.DARK_GREEN));
    }
}
