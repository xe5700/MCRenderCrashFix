package wang.magick.rendercrashfix;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Map;

/**
 * Created by xjboss on 2017/7/11.
 */
@IFMLLoadingPlugin.Name("RenderCrashFix")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class RenderFix implements IFMLLoadingPlugin {
    public RenderFix(){

    }
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{RenderClassesProcess.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }
    public static boolean FixRenderManager;
    public static boolean FixRenderItem;
    public static boolean FixRenderBlocks;
    public static boolean FixFMLHandshakeClientState$7;

    @Override
    public void injectData(Map<String, Object> data) {
        File file=new File("config","MCRenderCrashFix.conf");
        Configuration config=new Configuration(file);
        FixRenderManager=config.getBoolean("FixRenderManager","Config",true,"Pervent RenderManager.class to make client crash");
        FixRenderItem=config.getBoolean("FixRenderItem","Config",true,"Pervent RenderItem.class to make client crash");
        FixRenderBlocks=config.getBoolean("FixRenderBlocks","Config",true,"Pervent RenderBlocks.class to make client crash");
        FixFMLHandshakeClientState$7=config.getBoolean("FixFMLHandshakeClientState$7","Config",false,"To remove invoke revertToFrozen method in FixFMLHandshakeClientState$7.class.\nIf you are using same mods in same bungeecord server, you can only enable this option.");
        config.save();
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
