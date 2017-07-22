package wang.magick.rendercrashfix.Handlers;

import org.lwjgl.Sys;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * Created by xjboss on 2017/7/11.
 */
public abstract class ClassHandler implements Opcodes {
    public abstract byte[] handler(byte[] pClass);
}
