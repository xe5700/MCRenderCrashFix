package wang.magick.rendercrashfix;

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import wang.magick.rendercrashfix.Handlers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static wang.magick.rendercrashfix.RenderFix.*;

/**
 * Created by xjboss on 2017/7/11.
 */
public class RenderClassesProcess implements IClassTransformer {
    @Override
    public byte[] transform(String pName, String pTransformedName, byte[] pBasicClass) {
        if(FixRenderManager&&pTransformedName.equals("net.minecraft.client.renderer.entity.RenderManager")){
            return handler(RMHandler.class,pBasicClass);
        }
        else if(FixRenderItem&&pTransformedName.equals("net.minecraft.client.renderer.entity.RenderItem")){
            return handler(IRHandler.class,pBasicClass);
        }
        else if(FixRenderBlocks&&"net.minecraft.client.renderer.RenderBlocks".equals(pTransformedName)){
            return handler(BHHandler.class,pBasicClass);
        }
        else if(FixFMLHandshakeClientState$7&&"cpw.mods.fml.common.network.handshake.FMLHandshakeClientState$7".equals(pTransformedName)){
            return handler(FMLHS$7Handler.class,pBasicClass);
        }
        return pBasicClass;
    }
    private static <T extends ClassHandler> byte[] handler(Class<T> pClass, byte[] pFuckedClass){
        try {
            return pClass.newInstance().handler(pFuckedClass);
        }catch (Exception e){
            e.printStackTrace();
            return pFuckedClass;}
    }
    public byte[] testOutput(byte[] pData,String pName){
        try {
            FileOutputStream F = new FileOutputStream(new File(pName));
            F.write(pData);
            F.close();
        }catch (Exception e){}
        return pData;
    }
}
