package wang.magick.rendercrashfix.Handlers;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.tree.*;

/**
 * Created by xjboss on 2017/7/11.
 */
public class RMHandler extends ClassHandler {
    ClassNode cn=new MC();
    @Override
    public byte[] handler(byte[] pClass) {
        ClassReader cr=new ClassReader(pClass);
        cr.accept(cn, 0);
        ClassWriter cw=new ClassWriter(0);
        cn.accept(cw);
        return cw.toByteArray();
    }
    public class MC extends ClassNode{

        public MC() {
            super(ASM5);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv= super.visitMethod(access, name, desc, signature, exceptions);
            String method=FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(this.name,name,desc);
            if(method.equals("func_147939_a")){
                mv=new TB(mv);
            }
            return mv;
        }
    }
    public class TB extends MethodVisitor {
        public TB(MethodVisitor mv) {
            super(ASM5,mv);
        }
        int i = 0;
        boolean sss=false;
        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if (++i == 32) {
                super.visitInsn(ICONST_0);
                super.visitInsn(IRETURN);
                sss=true;
            }
        }

        @Override
        public void visitInsn(int opcode) {
            if(sss&&opcode!=-1)return;
            super.visitInsn(opcode);
        }
    }
}
