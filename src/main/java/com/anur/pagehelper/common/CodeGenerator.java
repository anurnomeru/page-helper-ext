package com.anur.pagehelper.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

public class CodeGenerator {

    //JDBC配置
    private static String JDBC_URL = "jdbc:mysql://localhost:3306/message_server?unicode=utf8mb4";

    private static String JDBC_USERNAME = "root";

    private static String JDBC_PASSWORD = "root";

    private static String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static String MODULE = "pagehelper";//TODO: 生成表要改这个

    //PACKAGE
    private static String PACKAGE_BASE = "com.anur." + MODULE;// 项目基础包名称

    private static String PACKAGE_MODEL = PACKAGE_BASE + ".model";// Model所在包

    private static String PACKAGE_MAPPER = PACKAGE_BASE + ".dao";// Mapper所在包

    private static String PACKAGE_SERVICE = PACKAGE_BASE + ".service";// Service所在包

    private static String PACKAGE_SERVICE_IMPL = PACKAGE_SERVICE + ".impl";// ServiceImpl所在包

    private static String PACKAGE_MAPPER_INTERFACE_REFERENCE = PACKAGE_BASE + ".core.Mapper";// Mapper插件基础接口的完全限定名

    //PATH
    private static String PATH_TEMPLATE_FILE = System.getProperty("user.dir") + "/codegen/src/main/resources/templates";//模板位置

    private static String PATH_PROJECT = System.getProperty("user.dir");
//        + "/" + MODULE;//项目在硬盘上的基础路径

    private static String PATH_SERVICE = PATH_PROJECT + "/src/main/java"; //Service文件路径

    private static String PATH_MAPPER = PATH_PROJECT + "/src/main/java"; //Mapper文件路径

    private static String PATH_MODEL = PATH_PROJECT + "/src/main/java"; //Model文件路径

    private static String PATH_MODEL_RESOURCES = PATH_PROJECT + "/src/main/resources";//Model映射文件路径

    private static String PATH_SERVICE_PACKAGE = PATH_SERVICE + packageConvertPath(PACKAGE_SERVICE);//生成的Service接口存放路径

    private static String PATH_SERVICE_IMPL_PACKAGE = PATH_SERVICE + packageConvertPath(PACKAGE_SERVICE_IMPL);//生成的Service实现存放路径

    //注释
    private static String AUTHOR = "Anur IjuoKaruKas";// @author

    private static String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        PATH_SERVICE = PATH_PROJECT + "/src/main/java";
        genCode("order_info");
    }

    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genModelAndMapper(tableName);
//            genService(tableName);
        }
    }

    public static void genModelAndMapper(String tableName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Mybatis");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", PACKAGE_MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PATH_MODEL);
        javaModelGeneratorConfiguration.setTargetPackage(PACKAGE_MODEL);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PATH_MODEL_RESOURCES);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PATH_MAPPER);
        javaClientGeneratorConfiguration.setTargetPackage(PACKAGE_MAPPER);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles()
                     .isEmpty() || generator.getGeneratedXmlFiles()
                                            .isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }

        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    //    public static void genService(String tableName) {
    //        try {
    //            freemarker.template.Configuration cfg = getConfiguration();
    //
    //            Map<String, Object> data = new HashMap<>();
    //            data.put("date", DATE);
    //            data.put("author", AUTHOR);
    //            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
    //            data.put("modelNameUpperCamel", modelNameUpperCamel);
    //            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
    //            data.put("basePackage", PACKAGE_BASE);
    //            data.put("modelPackage", PACKAGE_BASE);
    //
    //            File file = new File(PATH_SERVICE_PACKAGE + modelNameUpperCamel + "Service.java");
    //            if (!file.getParentFile().exists()) {
    //                file.getParentFile().mkdirs();
    //            }
    //            cfg.getTemplate("service.ftl").process(data,
    //                    new FileWriter(file));
    //            System.out.println(modelNameUpperCamel + "Service.java 生成成功");
    //
    //            File file1 = new File(PATH_SERVICE_IMPL_PACKAGE + modelNameUpperCamel + "ServiceImpl.java");
    //            if (!file1.getParentFile().exists()) {
    //                file1.getParentFile().mkdirs();
    //            }
    //            cfg.getTemplate("service-impl.ftl").process(data,
    //                    new FileWriter(file1));
    //            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
    //        } catch (Exception e) {
    //            throw new RuntimeException("生成Service失败", e);
    //        }
    //    }
    //
    //    private static freemarker.template.Configuration getConfiguration() throws IOException {
    //        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
    //        cfg.setDirectoryForTemplateLoading(new File(PATH_TEMPLATE_FILE));
    //        cfg.setDefaultEncoding("UTF-8");
    //        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    //        return cfg;
    //    }

    private static String tableNameConvertLowerCamel(String tableName) {
        StringBuilder result = new StringBuilder();
        if (tableName != null && tableName.length() > 0) {
            tableName = tableName.toLowerCase();//兼容使用大写的表名
            boolean flag = false;
            for (int i = 0; i < tableName.length(); i++) {
                char ch = tableName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        String camel = tableNameConvertLowerCamel(tableName);
        return camel.substring(0, 1)
                    .toUpperCase() + camel.substring(1);
    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
