package wang.magick.rendercrashfix.Handlers;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.*;
import wang.magick.rendercrashfix.Handlers.ClassHandler;

import java.util.ArrayList;

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
            if(method.equals("func_82406_b")){
                LabelNode ln1=new LabelNode();
                mv.instructions.add(ln1);
                mv.instructions.add(new InsnNode(RETURN));
                mv.instructions.add(new LabelNode());
                mv.tryCatchBlocks.get(0).handler=ln1;
            }
            else if(method.equals("func_94148_a")){
                LabelNode ln1=new LabelNode();
                LabelNode ln2=new LabelNode();
                LabelNode lastnode=(LabelNode) mv.instructions.getLast();
                ArrayList<LocalVariableNode> nodes=new ArrayList();
                for(LocalVariableNode lvn:mv.localVariables){
                    if(lvn.end==lastnode){
                        nodes.add(lvn);
                    }
                }
                LabelNode endnode=null;
                for(int in=mv.instructions.size()-1;in>=0;in--){
                    AbstractInsnNode node=mv.instructions.get(in);
                    if(node instanceof LabelNode){
                        if(node!=lastnode){
                            endnode=(LabelNode) node;
                            break;
                        }
                    }
                    mv.instructions.get(in);
                }
                int inde=mv.localVariables.size();
                LocalVariableNode v=new LocalVariableNode("e","Ljava/lang/Exception;",null,ln1,ln2,inde);
                mv.instructions.remove(lastnode);
                lastnode=new LabelNode();
                mv.instructions.add(ln1);
                mv.instructions.add(new LineNumberNode(1,ln1));
                mv.instructions.add(new FrameNode(F_SAME1, 0, null, 0, new Object[]{"java/lang/Exception"}));
                mv.instructions.add(new IntInsnNode(ASTORE,inde));
                mv.instructions.add(ln2);
                mv.instructions.add(new LineNumberNode(2,ln2));
                mv.instructions.add(new InsnNode(RETURN));
                mv.instructions.add(lastnode);
                mv.localVariables.add(0,v);
                mv.tryCatchBlocks.add(0,new TryCatchBlockNode((LabelNode) mv.instructions.get(0),endnode,ln1,"java/lang/Exception"));
                for(LocalVariableNode lvn:nodes){
                    lvn.end=lastnode;
                }
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
