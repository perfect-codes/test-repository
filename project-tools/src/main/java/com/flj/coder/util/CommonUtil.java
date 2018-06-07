package com.flj.coder.util;

import com.flj.CoderMojo;
import com.flj.coder.bo.ClassEntity;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/**
 * 工具类
 * @author xpf
 * @date 2018/6/4 下午2:29
 */
public class CommonUtil {

    private static final Version version = Configuration.VERSION_2_3_22;
    private static Configuration config;
    private final static String CLASSES_FLAG = "/target/classes";
    public static final int FLAG_PAGE = 1;
    public static final int FLAG_JAVACODE = 2;
    public static final int FLAG_BOTH = 3;
    private static final String ENCODING = "utf-8";

    static {
        CommonUtil.config = new Configuration(version);
        CommonUtil.config.setTemplateLoader(new ClassTemplateLoader(CoderMojo.class, "ftl/"));
        CommonUtil.config.setDefaultEncoding(ENCODING);
        CommonUtil.config.setEncoding(Locale.CHINA, ENCODING);
    }

    /**
     * java编译器
     *
     * @param javaFiles java文件集合
     * @param outPath   编译目录
     * @throws IOException
     */
    @Deprecated
    private void javac(Iterable<String> javaFiles, String outPath) throws IOException {
        if (javaFiles == null) {
            throw new RuntimeException();
        }
        // java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 文件管理器用于编译过程中的状态监听
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        // 加载java原文件集合
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjectsFromStrings(javaFiles);
        // 配置编译参数
        Iterable options = Arrays.asList("-d", outPath);
        // 装载编译任务
        CompilationTask t = compiler.getTask(null, manager, null, options, null, it);
        // 执行编译
        t.call();
        // 关闭管理器
        manager.close();
    }

    /**
     * 加载二进制文件
     * @param srcPath       F:/develop/runtime-EclipseApplication/test/src/
     * @param fullClassName com.perfect.domain.Student
     */
    @SuppressWarnings("resource")
    public Class<?> classLoad(String srcPath, String fullClassName) throws Exception {
        URLClassLoader ucl = new URLClassLoader(new URL[]{new URL("file:/" + srcPath)});
        Class<?> cls = ucl.loadClass(fullClassName);
        return cls;
    }

    /**
     * 获取@Comment注解
     * @param annotations
     * @return Annotation
     */
    public static Annotation getSpecialAnnotation(Annotation[] annotations) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if ("com.gj.annotition.Comment".equals(annotation.annotationType().getName())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 获取注解中的中文内容（值）
     * @param annotation
     * @return
     */
    public static String getComment(Annotation annotation) {
        String val = annotation.toString().split("=")[1];
        return val.substring(0, val.length() - 1);
    }

    /**
     * 根据类对象创建对应的一系列文件
     * @param pageRootPath
     * @param model
     * @param flag
     * @throws Exception
     */
    public static void createFiles(String pageRootPath, ClassEntity model, int flag) {

        try {
            if (FLAG_PAGE == flag || FLAG_BOTH == flag) {
                // page路径
                String pageFolerName = model.getClassName().toLowerCase();
                String pagePath = pageRootPath + "/" + pageFolerName;
                // 创建html页面
                createFile(model, pageFolerName + "_list.html", "list_page.ftl", pagePath);
                createFile(model, pageFolerName + "_add.html", "add_page.ftl", pagePath);
            }
            if (FLAG_JAVACODE == flag || FLAG_BOTH == flag) {
                //检查BaseController和BaseModel是否存在，不存在就创建
                createBaseFiles(model.getParentPackageName(), model.getParentPath());
                // repository路径
                String repositoryPath = model.getParentPath() + "/repository";
                // service路径
                String servicePath = model.getParentPath() + "/service";
                // controller路径
                String controllerPath = model.getParentPath() + "/web";
                // model路径
                String modelPath = model.getParentPath() + "/web/model";
                // 创建java文件
                createFile(model, model.getClassName() + "Repository.java", "repository.ftl", repositoryPath);
                createFile(model, model.getClassName() + "Service.java", "service.ftl", servicePath);
                createFile(model, model.getClassName() + "ServiceImpl.java", "service_impl.ftl", servicePath);
                createFile(model, model.getClassName() + "Controller.java", "controller.ftl", controllerPath);
                createFile(model, model.getClassName() + "Model.java", "model.ftl", modelPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据模型和模板名称创建指定文件名文件
     *
     * @param model
     * @param fileName
     * @param templateName
     * @param createPath
     */
    public static void createFile(ClassEntity model, String fileName, String templateName, String createPath) throws Exception{
            Template template = config.getTemplate(templateName, ENCODING);
            if (createPath != null) {
                File buildpath = new File(createPath);
                if (!buildpath.exists()) {
                    buildpath.mkdirs();
                }
            }
            File file = new File(createPath + "/" + fileName);
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ENCODING));
            Map<String, Object> map = new HashMap<>(1);
            map.put("bean", model);
            BeansWrapperBuilder builder = new BeansWrapperBuilder(version);
            BeansWrapper w1 = builder.build();
            template.process(map, fw, w1);
            fw.flush();
            fw.close();
    }

    /**
     * 创建BaseModel和BaseController
     * @param parentPackageName
     * @param parentPath
     */
    public static void createBaseFiles(String parentPackageName, String parentPath) throws Exception{
            Template template_model = config.getTemplate("base_model.ftl", ENCODING);
            Template template_controller = config.getTemplate("base_controller.ftl", ENCODING);
            File buildfolder = new File(parentPath + "/web/model");
            if (parentPath != null) {
                if (!buildfolder.exists()) {
                    buildfolder.mkdirs();
                }
            }
            File file_model = new File(buildfolder.getPath() + "/BaseModel.java");
            File file_controller = new File(buildfolder.getParentFile().getPath() + "/BaseController.java");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("parentPackageName", parentPackageName);
            BeansWrapperBuilder builder = new BeansWrapperBuilder(version);
            BeansWrapper w1 = builder.build();
            if (!file_model.exists()) {
                BufferedWriter fw_model = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_model), ENCODING));
                template_model.process(map, fw_model, w1);
                fw_model.flush();
                fw_model.close();
            }
            if (!file_controller.exists()) {
                BufferedWriter fw_controller = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_controller), ENCODING));
                template_controller.process(map, fw_controller, w1);
                fw_controller.flush();
                fw_controller.close();
            }
    }

    /**
     * 获取上一级包名
     * @author xpf
     * @date 2018/6/3 下午6:12
     */
    public static String getParentPackageName(String packageName){
        String[] parentPackageNameArray = packageName.split("[.]");
        StringBuffer parentPackageName = new StringBuffer();
        for (int i = 0; i < parentPackageNameArray.length - 1; i++) {
            parentPackageName.append(parentPackageNameArray[i]).append(".");
        }
        parentPackageName.deleteCharAt(parentPackageName.length() - 1);
        return parentPackageName.toString();
    }

}
