package wang.magick.rendercrashfix;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by xjboss on 2017/7/11.
 */
@IFMLLoadingPlugin.Name("RenderCrashFix")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class RenderFix implements IFMLLoadingPlugin {
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

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
