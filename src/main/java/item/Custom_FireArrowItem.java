package item;

import javax.annotation.Nullable;

import entity.custom.FireArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Custom_FireArrowItem extends ArrowItem {
	public Custom_FireArrowItem(Item.Properties properties) {
        super(properties);
    }
	
	@Override
    public AbstractArrow createArrow(Level level, ItemStack p_43238_, LivingEntity shooter, @Nullable ItemStack p_345773_) {
        return new FireArrowEntity(level, shooter, p_43238_.copyWithCount(1), p_345773_);
    }
}
