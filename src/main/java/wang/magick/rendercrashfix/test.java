package wang.magick.rendercrashfix;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Created by xjboss on 2017/7/14.
 */
public class test extends RenderBlocks {
    public IIcon getBlockIcon(final Block p_147793_1_, final IBlockAccess p_147793_2_, final int p_147793_3_, final int p_147793_4_, final int p_147793_5_, final int p_147793_6_) {
        try {
            return this.getIconSafe(p_147793_1_.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
        }catch (Exception e){
            return this.getIconSafe(null);
        }
    }
}
