package com.flj;

import com.flj.coder.bo.ClassEntity;
import com.flj.coder.bo.PropertyEntity;
import com.flj.coder.util.CommonUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @goal coder
 */
public class CoderMojo extends AbstractMojo {

    /**
     * @parameter expression="${project.build.directory}"
     * @required
     * @requiresDependencyResolution test
     */
    private File outputDirectory;
    /**
     * @parameter
     * @required
     */
    private String scanPackage;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!outputDirectory.exists()) {
            throw new MojoExecutionException("构建目录不存在");
        }
        String projectPath = outputDirectory.getParentFile().getAbsolutePath();
        String targetPath = outputDirectory.getAbsolutePath();
        String relativeBuildPath = "/classes/" + scanPackage.replace(".", "/");
        String srcPath = projectPath + "/src/";
        File file = new File(outputDirectory, relativeBuildPath);
        File[] children = file.listFiles();
        String relativeParentPackage = scanPackage.substring(0,scanPackage.lastIndexOf(".")).replace(".","/");
        String parentPath = srcPath + "main/java/"+ relativeParentPackage;
        for (File f : children) {
            String className = f.getName().substring(0,f.getName().length()-6);
            String fullClassName = scanPackage+"."+className;
            try {
                Class cls = classLoad(targetPath + "/classes/",fullClassName);
                ClassEntity classEntity = analysisClass(cls,scanPackage,className,parentPath);
                CommonUtil.createFiles(srcPath+"main/resources/pages",classEntity,CommonUtil.FLAG_BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载二进制文件
     *
     * @param buildPath       F:/develop/runtime-EclipseApplication/test/target/classes
     * @param fullClassName com.perfect.domain.Student
     */
    public Class<?> classLoad(String buildPath, String fullClassName) throws Exception {
        URLClassLoader ucl = new URLClassLoader(new URL[]{new URL("file:" + buildPath)});
        Class<?> cls = ucl.loadClass(fullClassName);
        return cls;
    }


    public ClassEntity analysisClass(Class<?> clazz,String packageName,String className,String parentPath){
        // 封装类实体
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(className);
        classEntity.setFileName(className+".class");
        classEntity.setPackageName(packageName);
        classEntity.setParentPackageName(CommonUtil.getParentPackageName(packageName));
        classEntity.setParentPath(parentPath);

        List<PropertyEntity> properties = new ArrayList<PropertyEntity>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if("serialVersionUID".equals(field.getName())){
                continue;
            }
            String fieldTypeFullName = field.getType().getName();
            String[] fieldTypeFullNameArray = fieldTypeFullName.split("[.]");
            String fieldTypeName = fieldTypeFullNameArray[fieldTypeFullNameArray.length - 1];
            PropertyEntity pro = new PropertyEntity();
            pro.setFieldName(field.getName());
            pro.setFieldTypeName(fieldTypeName);
            pro.setFieldTypeFullName(fieldTypeFullName);
            pro.setFieldComment(field.getName());
            properties.add(pro);
//            Annotation annotation = null;
//            if ((annotation = commonUtil.getSpecialAnnotation(field.getAnnotations())) != null) {
//                pro.setFieldComment(commonUtil.getComment(annotation));
//            }
//            classEntity.setProperties(properties);
        }
        classEntity.setProperties(properties);
        return classEntity;
    }
}
