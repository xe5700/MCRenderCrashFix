package wang.magick.rendercrashfix.Handlers;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

/**
 * Created by xjboss on 2017/7/14.
 */
public class BHHandler extends ClassHandler {
    ClassNode cn=new ClassNode();
    MethodNode getIconsafe;
    @Override
    public byte[] handler(byte[] pClass) {
        ClassReader cr=new ClassReader(pClass);
        cr.accept(cn, 0);
        for(MethodNode mn:cn.methods){
            String method= FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name,mn.name,mn.desc);
            if(method.equals("func_147758_b")){
                getIconsafe=mn;
                break;
            }
        }
        for(MethodNode mn:cn.methods){
            String method= FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name,mn.name,mn.desc);
            if(method.equals("func_147793_a")){
                LabelNode lb1=null;
                LabelNode lb2=null;
                int i=0;
                AbstractInsnNode before=null;
                for(AbstractInsnNode insn=mn.instructions.getFirst();insn!=null;insn=insn.getNext()){
                    if(insn instanceof LabelNode){
                        if(lb1==null){
                            lb1=(LabelNode)insn;
                        }else{
                            lb2=(LabelNode)insn;
                            break;
                        }
                    }
                    before=insn;
                }
                mn.instructions.remove(before);
                mn.instructions.add(new InsnNode(ARETURN));
                LabelNode lb3=new LabelNode(new Label());
                int inde=mn.localVariables.size();
                mn.instructions.add(lb3);
                mn.instructions.add(new LineNumberNode(1,lb3));
                mn.instructions.add(new FrameNode(Opcodes.F_SAME1, 0, null, 0, new Object[]{"java/lang/Exception"}));
                mn.instructions.add(new IntInsnNode(ASTORE,inde));
                LabelNode lb4=new LabelNode();
                mn.instructions.add(lb4);
                mn.instructions.add(new LineNumberNode(2,lb4));
                mn.instructions.add(new IntInsnNode(ALOAD,0));
                mn.instructions.add(new InsnNode(ACONST_NULL));
                mn.instructions.add(new MethodInsnNode(INVOKEVIRTUAL,cn.name,getIconsafe.name,getIconsafe.desc));
                mn.instructions.add(new InsnNode(ARETURN));
                LabelNode lb5=new LabelNode();
                mn.instructions.add(lb5);
                for(LocalVariableNode lvn:mn.localVariables){
                    lvn.end=lb5;
                }
                LocalVariableNode lvn=new LocalVariableNode("e","Ljava/lang/Exception;",null,lb4,lb5,inde);
                mn.localVariables.add(0,lvn);
                mn.tryCatchBlocks.add(new TryCatchBlockNode(lb1,lb2,lb3,"java/lang/Exception"));
                //mn.maxStack++;
                mn.maxLocals++;
                break;
            }
        }
        ClassWriter cw=new ClassWriter(0);
        cn.accept(cw);
        return cw.toByteArray();
    }
    public class MV extends GeneratorAdapter{
        public MV(MethodVisitor mv, int access, String name, String desc) {
            super(ASM5,mv, access, name, desc);
        }
        Label l1;
        Label l2;
        Label l3;
        int time=1;
        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if(l1==null)l1=label;
            else if(l2==null){
                l2=label;
                l3=newLabel();
                visitLabel(label);
            }
            else if(l3==label&&time==2){
                visitInsn(ACONST_NULL);
                invokeVirtual(Type.getType(cn.name),new Method(getIconsafe.name,getIconsafe.desc));
                visitInsn(ARETURN);
            }
        }

        @Override
        public void visitEnd() {
            time++;
            super.visitEnd();
        }
    }
}
