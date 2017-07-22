package wang.magick.rendercrashfix.Handlers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

/**
 * Created by xjboss on 2017/7/22.
 */
public class FMLHS$7Handler extends ClassHandler {
    @Override
    public byte[] handler(byte[] pClass) {
        ClassReader cr=new ClassReader(pClass);
        ClassNode cn=new ClassNode();
        cr.accept(cn, 0);
        for (MethodNode mv:cn.methods){
            if(mv.name.equals("accept")){
                AbstractInsnNode node=mv.instructions.getFirst();
                do{
                    if(node instanceof MethodInsnNode){
                        if(node.getOpcode()==INVOKESTATIC){
                            if(((MethodInsnNode) node).name.equals("revertToFrozen")){
                                mv.instructions.remove(node);
                                break;
                            }
                        }
                    }
                }while ((node=node.getNext())!=null);
                break;
            }
        }
        ClassWriter cw=new ClassWriter(0);
        cn.accept(cw);
        return cw.toByteArray();
    }
}
