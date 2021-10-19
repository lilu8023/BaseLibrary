package com.lilu.apptool.gson;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.TypeAdapterFactory;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public class NonNullFieldFactory implements TypeAdapterFactory {

    private static final String ANNOTATION_NAME = NonNullField.class.getSimpleName();
    /**
     * 保存Type及其对应的NonNullField
     */
    private static final Map<Type, List<Field>> fieldMap = new ConcurrentHashMap<>();
    /**
     * InstanceCreator缓存
     */
    private static final Map<Class<? extends InstanceCreator>, InstanceCreator> creatorCache = new ConcurrentHashMap<>();

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

        List<Field> fields = findMatchedFields(typeToken);
        final Type type = typeToken.getType();

        // 如果找到了，则包裹一层Adapter
        if (fields != null && !fields.isEmpty()) {
            fieldMap.put(type, fields);

            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, typeToken);
            log("create wrapper adapter, type = %s, find %d fields, delegate = %s", typeToken, fields.size(), delegate);
            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public T read(JsonReader in) throws IOException {
                    T t = delegate.read(in);
                    log("  finish read, data = %s, type = %s, delegate = %s", t, type, delegate);
                    replaceNonNullFields(t, typeToken);
                    return t;
                }
            };
        }
        return null;
    }

    private static void log(String msg, Object... args) {
    }

    /**
     * 是否需要搜索Type中的Field
     */
    @SuppressWarnings("RedundantIfStatement")
    private static boolean shouldSearch(Class clazz) {
        // 跳过不需要搜索的类
        if (clazz == null || clazz == Object.class || clazz.isPrimitive() || clazz.isEnum() || clazz.isArray()) {
            log("skip search class %s", clazz);
            return false;
        }
        // 跳过Java和Android系统中的类
        String packageName = clazz.getPackage().getName();
        if (packageName.startsWith("java") || packageName.startsWith("android")) {
            log("skip search class %s by package", clazz);
            return false;
        }
        // 只匹配特定的类、跳过其他第三方库的类……
        return true;
    }

    /**
     * 找到某个Type中的NonNullField，包括继承的
     */
    private static List<Field> findMatchedFields(TypeToken typeToken) {
        List<Field> list = null;
        Class raw = typeToken.getRawType();
        while (shouldSearch(raw)) {
            Field[] fields = raw.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getAnnotation(NonNullField.class) != null) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(field);
                }
            }
            // 解析父类
            typeToken = TypeToken.get($Gson$Types.resolve(typeToken.getType(), typeToken.getRawType(), raw.getGenericSuperclass()));
            raw = typeToken.getRawType();
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    /**
     * 解析Field的Type，处理泛型参数
     *
     * @param typeToken Field所在类的Type
     * @param field     要解析的Field
     */
    private static Type resolveFieldType(TypeToken typeToken, Field field) {
        return $Gson$Types.resolve(typeToken.getType(), typeToken.getRawType(), field.getGenericType());
    }

    /**
     * 填充对象中的NonNullField
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void replaceNonNullFields(Object o, TypeToken typeToken) {
        if (o == null) {
            return;
        }
        // 对于嵌套注解的情况（NonNullField对应类型中又有NonNullField），
        // 由于Gson会先解析内部数据，其TypeAdapter已经创建，此处map可以取到值
        List<Field> fields = fieldMap.get(typeToken.getType());
        if (fields == null || fields.isEmpty()) {
            return;
        }
        for (Field field : fields) {
            try {
                Object fieldValue = field.get(o);
                if (fieldValue == null) {
                    Object value = constructField(field, resolveFieldType(typeToken, field));
                    if (value == null) {
                        throw new RuntimeException(String.format("Create field %s for type %s failure",
                                field.getName(), typeToken.getType()));
                    }
                    field.set(o, value);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static Object constructField(Field field, Type type) {
        NonNullField annotation = field.getAnnotation(NonNullField.class);
        Class<? extends InstanceCreator> creatorClass = annotation.value();
        InstanceCreator creator = getCreator(creatorClass);
        Object instance = creator.createInstance(type);
        replaceNonNullFields(instance, TypeToken.get(type));
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static synchronized InstanceCreator getCreator(Class<? extends InstanceCreator> creatorClass) {
        InstanceCreator creator = creatorCache.get(creatorClass);
        if (creator == null) {
            try {
                creator = creatorClass.newInstance();
                creatorCache.put(creatorClass, creator);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("InstanceCreator " + creatorClass + " create failure", e);
            }
        }
        return creator;
    }
}
