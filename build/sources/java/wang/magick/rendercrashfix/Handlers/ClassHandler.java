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
    abstract public byte[] handler(byte[] pClass);
    public class addingTryCatchVoid extends GeneratorAdapter {
        public addingTryCatchVoid(MethodVisitor mv, int access, String name, String desc) {
            super(ASM5,mv, access, name, desc);
        }
        boolean st=false;
        Label back=newLabel();
        Label end_l=newLabel();
        @Override
        public void visitLabel(Label label) {
            if(!st){
                st=true;
                visitTryCatchBlock(label,end_l,back,"java/lang/Exception");
            }
            super.visitLabel(label);
        }

        @Override
        public void visitEnd() {
            visitLabel(end_l);
            visitLabel(back);
            returnValue();
            super.visitEnd();
        }
    }
    public class addingTryCatchFalse extends GeneratorAdapter {
        public addingTryCatchFalse(MethodVisitor mv, int access, String name, String desc) {
            super(ASM5,mv, access, name, desc);
        }
        boolean st=false;
        Label back=newLabel();
        Label end_l=newLabel();
        @Override
        public void visitLabel(Label label) {
            if(!st){
                st=true;
                visitTryCatchBlock(label,end_l,back,"java/lang/Exception");
            }
            super.visitLabel(label);
        }

        @Override
        public void visitEnd() {
            visitLabel(end_l);
            visitLabel(back);
            push(false);
            returnValue();
            super.visitEnd();
        }
    }
}
