package wang.magick.rendercrashfix;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import wang.magick.rendercrashfix.Handlers.BHHandler;
import wang.magick.rendercrashfix.Handlers.RMHandler;
import wang.magick.rendercrashfix.Handlers.IRHandler;
import wang.magick.rendercrashfix.Handlers.ClassHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by xjboss on 2017/7/11.
 */
public class RenderClassesProcess implements IClassTransformer {
    public static final boolean obfuscated;
    static
    {
        boolean ooo=false;
        try {
            ooo=Launch.classLoader.getClassBytes("net.minecraft.world.World") != null;
        }
        catch (IOException e) {}
        obfuscated=!ooo;
    }

    @Override
    public byte[] transform(String pName, String pTransformedName, byte[] pBasicClass) {
        if(pTransformedName.equals("net.minecraft.client.renderer.entity.RenderManager")){

            byte[] b= handler(RMHandler.class,pBasicClass);
            return b;

        }
        else if(pTransformedName.equals("net.minecraft.client.renderer.entity.RenderItem")){
            byte[] b= handler(IRHandler.class,pBasicClass);
            return b;
        }
        else if(pTransformedName.equals("net.minecraft.client.renderer.RenderBlocks")){

            byte[] b= handler(BHHandler.class,pBasicClass);
            return b;

        }
        return pBasicClass;
    }
    public static <T extends ClassHandler> byte[] handler(Class<T> pClass,byte[] pFuckedClass){
        try {
            return pClass.newInstance().handler(pFuckedClass);
        }catch (Exception e){
            e.printStackTrace();
            return pFuckedClass;}
    }
}
