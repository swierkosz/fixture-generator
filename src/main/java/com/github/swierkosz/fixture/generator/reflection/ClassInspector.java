package com.github.swierkosz.fixture.generator.reflection;
/*
 *    Copyright 2020 Szymon Świerkosz
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.swierkosz.fixture.generator.TypeInformation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.swierkosz.fixture.generator.reflection.ReflectionUtils.setValue;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.singletonList;

public class ClassInspector {

    public Set<FieldInformation> listFieldsFor(Type type) {
        Set<FieldInformation> fields = new HashSet<>();

        while (!Object.class.equals(type)) {
            Class<?> currentClass;
            Map<TypeVariable<?>, Type> currentClassParameters = new HashMap<>();

            if (type instanceof Class) {
                currentClass = (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                currentClass = (Class<?>) parameterizedType.getRawType();

                TypeVariable<?>[] typeParameters = currentClass.getTypeParameters();

                int i = 0;
                for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                    currentClassParameters.put(typeParameters[i], typeArgument);
                    i++;
                }
            } else {
                throw new UnableToResolveTypeException("Unable to resolve type: " + type.getTypeName());
            }

            for (Field field : currentClass.getDeclaredFields()) {
                if (isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);

                FieldInformation fieldInformation = new FieldInformation(
                        field.getName(),
                        (target, value) -> setValue(target, field, value),
                        resolve(field.getGenericType(), currentClassParameters));

                fields.add(fieldInformation);
            }

            type = currentClass.getGenericSuperclass();
        }

        return fields;
    }

    private static TypeInformation resolve(Type type, Map<TypeVariable<?>, Type> mapping) {
        if (type instanceof Class<?>) {
            return resolveClass((Class<?>) type, mapping);
        } else if (type instanceof ParameterizedType) {
            return resolveParametrizedType((ParameterizedType) type, mapping);
        } else if (type instanceof GenericArrayType) {
            return resolveGenericArrayType((GenericArrayType) type, mapping);
        } else if (type instanceof TypeVariable && mapping.containsKey(type)) {
            return resolve(mapping.get(type), mapping);
        } else {
            throw new UnableToResolveTypeException("Unable to resolve type: " + type.getTypeName());
        }
    }

    private static TypeInformation resolveClass(Class<?> aClass, Map<TypeVariable<?>, Type> mapping) {
        if (aClass.isArray()) {
            List<TypeInformation> typeParameters = new ArrayList<>();
            typeParameters.add(resolve(aClass.getComponentType(), mapping));
            return new TypeInformationImpl(aClass, typeParameters);
        } else {
            return new TypeInformationImpl(aClass);
        }
    }

    private static TypeInformation resolveParametrizedType(ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> mapping) {
        List<TypeInformation> typeParameters = new ArrayList<>();
        for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
            typeParameters.add(resolve(actualTypeArgument, mapping));
        }
        return new TypeInformationImpl((Class<?>) parameterizedType.getRawType(), typeParameters);
    }

    private static TypeInformation resolveGenericArrayType(GenericArrayType genericArrayType, Map<TypeVariable<?>, Type> mapping) {
        TypeInformation componentType = resolve(genericArrayType.getGenericComponentType(), mapping);
        // TODO: Is there a better way to obtain this?
        Class<?> arrayClass = Array.newInstance(componentType.getRawType(), 0).getClass();
        return new TypeInformationImpl(arrayClass, singletonList(componentType));
    }
}
