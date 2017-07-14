package wang.magick.rendercrashfix.Handlers;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.*;
import wang.magick.rendercrashfix.Handlers.ClassHandler;

/**
 * Created by xjboss on 2017/7/11.
 */
public class IRHandler extends ClassHandler {
    @Override
    public byte[] handler(byte[] pClass) {
        ClassReader cr=new ClassReader(pClass);
        ClassNode cn=new ClassNode();
        MethodVisitor mm=null;
        cr.accept(cn, 0);
        int ai=0;
        int i=0;
        for (MethodNode mv:cn.methods){
            String method=FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name,mv.name,mv.desc);
            LabelNode ln=new LabelNode();
            if(method.equals("func_82406_b")){
                mv.instructions.add(ln);
                mv.instructions.add(new InsnNode(RETURN));
                mv.instructions.add(new LabelNode());
                mv.tryCatchBlocks.get(0).handler=ln;
            }
        }
        ClassWriter cw=new ClassWriter(0);
        cn.accept(cw);
        return cw.toByteArray();
    }
    public class TB extends MethodVisitor {
        public TB(MethodVisitor mv) {
            super(ASM5, mv);
        }
        int i=0;
        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if(++i==16){
                super.visitInsn(RETURN);
                visitLabel(new Label());
            }
        }

    }
}
