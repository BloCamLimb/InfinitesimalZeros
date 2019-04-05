package infinitesimalzeros.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.util.interfaces.IMaterialHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;

public class AnnotatedMaterialHandlerLoader {
	
	public static void discoverAndLoadAnnotatedMaterialHandlers(ASMDataTable asmDataTable) {
        Set<ASMData> annotations = asmDataTable.getAll(IMaterialHandler.RegisterMaterialHandler.class.getName());
        int materialHandlersRegistered = 0;
        for(ASMData annotationData : annotations) {
            String materialHandlerClassName = annotationData.getClassName();
            Class<?> materialHandlerClass;
            try {
                materialHandlerClass = Class.forName(materialHandlerClassName);
            } catch (ClassNotFoundException exception) {
                continue;
            }
            String denyReason = null;
            Throwable loadingError = null;

            Field instanceField = null;
            Constructor<?> constructor = null;

            if(!IMaterialHandler.class.isAssignableFrom(materialHandlerClass)) {
                denyReason = "class does not implement IMaterialHandler";
            } else if(Modifier.isAbstract(materialHandlerClass.getModifiers())) {
                denyReason = "class is abstract and cannot be initialized";
            }
            try {
                instanceField = materialHandlerClass.getField("INSTANCE");
                if(instanceField.getType() != materialHandlerClass) {
                    denyReason = "found INSTANCE field, but it's type is not the same as class type";
                } else if(!Modifier.isStatic(instanceField.getModifiers())) {
                    denyReason = "found INSTANCE field, but it's not static and cannot be used";
                }
            } catch (NoSuchFieldException noSuchField) {
                try {
                    constructor = materialHandlerClass.getConstructor();
                } catch (NoSuchMethodException noSuchMethod) {
                    denyReason = "didn't found public static final INSTANCE field or public no-arg constructor for initialization";
                }
            }

            IMaterialHandler materialHandler = null;
            if(denyReason == null) {
                if(instanceField != null) {
                    try {
                        materialHandler = (IMaterialHandler) instanceField.get(null);
                    } catch (ReflectiveOperationException exception) {
                        denyReason = "failed to retrieve INSTANCE field value";
                        loadingError = exception;
                    }
                } else {
                    try {
                        materialHandler = (IMaterialHandler) constructor.newInstance();
                    } catch (ReflectiveOperationException exception) {
                        denyReason = "failed to initialize material handler";
                        loadingError = exception;
                    }
                }
            }
            if(denyReason == null) {
                MaterialBasis.registerMaterialHandler(materialHandler);
                materialHandlersRegistered++;
            }
        }
    }

}
