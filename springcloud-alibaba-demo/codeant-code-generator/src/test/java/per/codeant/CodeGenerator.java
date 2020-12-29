package per.codeant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
public class CodeGenerator {

    //驱动程序名
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名
//    public static final String URL = "jdbc:mysql://localhost:3306/golf_import?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
    public static final String URL = "jdbc:mysql://123.56.129.198:33306/nacos?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
    //MySQL配置时的用户名
//    public static final String USER_NAME = "root";
    public static final String USER_NAME = "root";
    //MySQL配置时的密码
//    public static final String PASSWORD = "123456";
    public static final String PASSWORD = "codeAnt.+AA123";
    //生成的文件字符集
    public static final String FILE_CHAR_SET = "utf-8";
    //匹配的表名（%代表全部）
    public static final String TABLE_NAME = "%";
    //生成的实体类的文件路径（如果设置为null则不会生成entity文件）
//    public static final String ENTITY_FILE_PATH = "golf-booking/src/main/java/com/golf/common/database/entity";
//    public static final String ENTITY_FILE_PATH = "E:/workspace/golf/golf-core/src/main/java/com/golf/common/entity";
    public static final String ENTITY_FILE_PATH = "codeant-common/common-entity/src/main/java/per/codeant/common/entity";
    //生成proxy接口的文件路径（如果设置为null则不会生成proxy文件）
//    public static final String PROXY_FILE_PATH = null;//    public static final String PROXY_FILE_PATH = "golf-booking/src/main/java/com/golf/common/database/proxy";
//    public static final String PROXY_FILE_PATH = "E:/workspace/golf/golf-core/src/main/java/com/golf/common/proxy";
    public static final String PROXY_FILE_PATH = "codeant-common/common-entity/src/main/java/per/codeant/common/proxy";
    //生成Mapper.xml文件的路径（如果设置为null则不会生成mapper文件）
//    public static final String MAPPER_FILE_PATH = null;
//    public static final String MAPPER_FILE_PATH = "golf-booking/src/main/java/com/golf/common/database/mapper";
//    public static final String MAPPER_FILE_PATH = "E:/workspace/golf/golf-core/src/main/java/com/golf/common/mapper";
    public static final String MAPPER_FILE_PATH = "codeant-common/common-entity/src/main/java/per/codeant/common/mapper";


    //生成实体类的包名
    public static final String ENTITY_PAGE_NAME = getPackageName(ENTITY_FILE_PATH);
    //生成proxy接口的包名
    public static final String PROXY_PAGE_NAME = getPackageName(PROXY_FILE_PATH);



    public static void main(String[] args) throws Exception {
        System.out.println("开始注册驱动！");
        Class.forName(DRIVER);
        //1.getConnection()方法，连接MySQL数据库！！
        System.out.println("开始连接数据库！");
        Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        Statement stmt = conn.createStatement();

        System.out.println("获取数据库表结构信息！");
        DatabaseMetaData meta = conn.getMetaData();
        //目录名称; 数据库名; 表名称; 表类型;
        ResultSet rs = meta.getTables(conn.getCatalog(), URL, TABLE_NAME, new String[]{"TABLE"});
        long s = System.currentTimeMillis();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String remark = getTableRemark(stmt, tableName);
            generateEntityFiles(conn,meta, tableName, remark);
            generateProxyFiles(conn,meta, tableName);
            generateXMLFile(conn,meta, tableName);
        }
        long e = System.currentTimeMillis();
        System.out.println("代码生成完成，共耗时" + (e - s) + "毫秒！");

    }

    public static String getPackageName(String path) {
        if (path == null || path.isEmpty()) return path;
        return path.split("/src/main/java/")[1].replaceAll("/", ".");
    }

    private static String getTableRemark(Statement stmt, String tableName) throws SQLException {
        String remark = null;
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        if (rs != null && rs.next()) {
            remark = rs.getString(2);
            int index = remark.lastIndexOf("COMMENT='");
            if (index == -1) {
                remark = null;
            } else {
                remark = remark.substring(index + 9, remark.length() - 1);
            }
        }
        return remark;
    }


    private static String getAttrValue(String str, String attrName) {
        int start = str.indexOf(attrName);
        if (start == -1) return null;
        int index1 = -1;
        int index2 = -1;
        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\'' || c == '"') {
                if (index1 != -1) {
                    index2 = i;
                    break;
                }
                if (index1 == -1) {
                    index1 = i;
                }
            }
        }
        if (index1 != -1 && index2 != -1) {
            return str.substring(index1 + 1, index2);
        }
        return null;
    }


    private static String getResultMapText(List<Map<String, String>> columnInfoList, Map<String, String> primaryKey) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n");
        for (Map<String, String> columnInfo : columnInfoList) {
            if (columnInfo.get("dataColumnName").equals(primaryKey.get("dataColumnName"))) {
                builder.append("\t\t<id column=\"");
            } else {
                builder.append("\t\t<result column=\"");
            }
            builder.append(columnInfo.get("dataColumnName"));
            builder.append("\"");
            builder.append(" property=\"");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append("\"");
            builder.append(" jdbcType=\"");
            builder.append(columnInfo.get("jdbcType"));
            builder.append("\"/>\r\n");
        }
        builder.append("\t");
        return builder.toString();
    }

    private static String getSqlText(List<Map<String, String>> columnInfoList) {
        StringBuilder builder = new StringBuilder();
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t");
            builder.append(columnInfo.get("dataColumnName"));
            builder.append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append("\r\n\t");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private static String getSelectText(Map<String, String> primaryKey, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tSELECT \r\n");
        builder.append("\t\t\t<include refid=\"Base_Column_List\"/> ");
        builder.append("\r\n\t\tFROM \r\n\t\t\t");
        builder.append(tableName);
        builder.append(" \r\n\t\tWHERE \r\n\t\t\t");
        builder.append(primaryKey.get("dataColumnName"));
        builder.append(" = #{");
        builder.append(primaryKey.get("javaColumnName"));
        builder.append(",jdbcType=");
        builder.append(primaryKey.get("jdbcType"));
        builder.append("}\r\n\t");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private static String getDeleteText(Map<String, String> primaryKey, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tDELETE FROM \r\n\t\t\t");
        builder.append(tableName);
        builder.append(" \r\n\t\tWHERE \r\n\t\t\t");
        builder.append(primaryKey.get("dataColumnName"));
        builder.append(" = #{");
        builder.append(primaryKey.get("javaColumnName"));
        builder.append(",jdbcType=");
        builder.append(primaryKey.get("jdbcType"));
        builder.append("}\r\n\t");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private static String getInsertText(List<Map<String, String>> columnInfoList, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tINSERT INTO \r\n\t\t\t");
        builder.append(tableName);
        builder.append(" (");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t\t");
            builder.append(columnInfo.get("dataColumnName"));
            builder.append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append("\r\n\t\t\t) VALUES (");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t\t#{");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(",jdbcType=");
            builder.append(columnInfo.get("jdbcType"));
            builder.append("},");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append("\r\n\t\t\t)\r\n\t");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private static String getInsertSelectiveText(List<Map<String, String>> columnInfoList, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tINSERT INTO \r\n\t\t\t");
        builder.append(tableName);
        builder.append("\r\n\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Map<String, String> columnInfo : columnInfoList) {

            builder.append("\r\n\t\t\t<if test=\"");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(" != null\">");
            builder.append("\r\n\t\t\t\t");
            builder.append(columnInfo.get("dataColumnName"));
            builder.append(",");
            builder.append("\r\n\t\t\t</if>");
        }
        builder.append("\r\n\t\t</trim>");
        builder.append("\r\n\t\t<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t<if test=\"");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(" != null\">");
            builder.append("\r\n\t\t\t\t#{");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(",jdbcType=");
            builder.append(columnInfo.get("jdbcType"));
            builder.append("},");
            builder.append("\r\n\t\t\t</if>");
        }
        builder.append("\r\n\t\t</trim>\r\n\t");
        return builder.toString();
    }


    @SuppressWarnings("unused")
	private static String getUpdateSelectiveText(Map<String, String> primaryKey, List<Map<String, String>> columnInfoList, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tUPDATE \r\n\t\t\t");
        builder.append(tableName);
        builder.append("\r\n\t\t<set>");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t<if test=\"");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(" != null\">");
            builder.append("\r\n\t\t\t\t");

            builder.append(columnInfo.get("dataColumnName"));
            builder.append(" = ");
            builder.append("#{");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(",jdbcType=");
            builder.append(columnInfo.get("jdbcType"));
            builder.append("},");
            builder.append("\r\n\t\t\t</if>");
        }
        builder.append("\r\n\t\t</set>");
        builder.append(" \r\n\t\tWHERE \r\n\t\t\t");
        builder.append(primaryKey.get("dataColumnName"));
        builder.append(" = ");
        builder.append("#{");
        builder.append(primaryKey.get("javaColumnName"));
        builder.append(",jdbcType=");
        builder.append(primaryKey.get("jdbcType"));
        builder.append("}\r\n\t");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private static String getUpdateText(Map<String, String> primaryKey, List<Map<String, String>> columnInfoList, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\t\tUPDATE \r\n\t\t\t");
        builder.append(tableName);
        builder.append(" \r\n\t\tSET ");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t");
            builder.append(columnInfo.get("dataColumnName"));
            builder.append(" = ");
            builder.append("#{");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(",jdbcType=");
            builder.append(columnInfo.get("jdbcType"));
            builder.append("},");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append(" \r\n\t\tWHERE \r\n\t\t\t");
        builder.append(primaryKey.get("dataColumnName"));
        builder.append(" = ");
        builder.append("#{");
        builder.append(primaryKey.get("javaColumnName"));
        builder.append(",jdbcType=");
        builder.append(primaryKey.get("jdbcType"));
        builder.append("}\r\n\t");
        return builder.toString();
    }


    private static String getBatchInsert(Map<String, String> primaryKey, List<Map<String, String>> columnInfoList, String tableName) {
        StringBuilder builder = new StringBuilder();
//        builder.append("\r\n\t\t<selectKey resultType=\"");
//        builder.append(fullNameOfClass(primaryKey.get("javaType")));
//        builder.append("\" keyProperty=\"");
//        builder.append(primaryKey.get("javaColumnName"));
//        builder.append("\" order=\"AFTER\">\r\n\t\t\t");
//        builder.append("SELECT LAST_INSERT_ID()\r\n\t\t");
//        builder.append("</selectKey>");

        builder.append("\r\n\t\tINSERT INTO ");
        builder.append(tableName);
        builder.append("\r\n\t\t\t (");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t\t");
            builder.append(columnInfo.get("dataColumnName"));
            builder.append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append("\r\n\t\t\t) VALUES \r\n\t\t");
        builder.append("<foreach collection=\"list\" item=\"record\" separator=\",\" index=\"index\">\r\n\t\t\t(");
        for (Map<String, String> columnInfo : columnInfoList) {
            builder.append("\r\n\t\t\t\t#{record.");
            builder.append(columnInfo.get("javaColumnName"));
//            builder.append(",jdbcType=");
//            String jdbcType = columnInfo.get("jdbcType");
//            if(jdbcType.equalsIgnoreCase("text") || jdbcType.equalsIgnoreCase("JSON")){
//                jdbcType = "VARCHAR";
//            }
//            builder.append(jdbcType);
            builder.append("},");
        }
        builder.delete(builder.length() - 1, builder.length());
        builder.append("\r\n\t\t\t)\r\n\t\t</foreach>\r\n\t");
        return builder.toString();
    }

    private static String getTitle(String str) {
        int index1 = str.indexOf("<?");
        int index2 = str.indexOf("?>");
        return str.substring(index1 + 2, index2);
    }

    /**
     * 根据ID和标签明删除其中的文本数据
     *
     * @param builder
     * @param tagName
     * @param id
     */
    private static void deleteTextByTagNameAndID(StringBuilder builder, String tagName, String id) {
        int cursor = -1;
        while ((cursor = builder.indexOf("<" + tagName, cursor + 1)) != -1) {
            int begin = builder.indexOf(">", cursor);
            if (begin == -1) throw new RuntimeException(tagName + "标签格式错误！");
            if (id.equals(getAttrValue(builder.substring(cursor, begin + 1), "id"))) {
                if (builder.charAt(begin - 1) == '/') return;
                int end = builder.indexOf("</" + tagName + ">", begin);
                if (end == -1) throw new RuntimeException(tagName + "标签没有闭合！");
                builder.delete(begin + 1, end);
                break;
            }
        }
    }

    /**
     * 根据标签名和ID插入文本数据
     *
     * @param builder
     * @param tagName
     * @param id
     * @param text
     */
    private static void insertTextByTagNameAndID(StringBuilder builder, String tagName, String id, String text) {
        int cursor = -1;
        while ((cursor = builder.indexOf("<" + tagName, cursor + 1)) != -1) {
            int begin = builder.indexOf(">", cursor);
            if (begin == -1) throw new RuntimeException(tagName + "标签格式错误！");
            if (id.equals(getAttrValue(builder.substring(cursor, begin + 1), "id"))) {
                if (builder.charAt(begin - 1) == '/') break;
                builder.insert(begin + 1, text);
                break;
            }
        }
    }


    /**
     * 生成小xml文件
     *
     * @param metaData
     * @param tableName
     * @throws IOException
     * @throws SQLException
     */
    public static void generateXMLFile(Connection conn,DatabaseMetaData metaData, String tableName) throws IOException, SQLException {
        if (MAPPER_FILE_PATH == null) return;
        long s = System.currentTimeMillis();
        File dir = new File(MAPPER_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File xmlFile = new File(dir, firstUppercase(underlineToHump(tableName)) + "Mapper.xml");

        if (xmlFile.exists()) {
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            byte[] fileData = new byte[(int) xmlFile.length()];
            fileInputStream.read(fileData);
            fileInputStream.close();
            String str = new String(fileData);

            Map<String, String> primaryKey = getPrimaryKey(metaData, tableName);
            List<Map<String, String>> columnInfoList = getColumnInfoList(conn,metaData, tableName);
            String encoding = getAttrValue(getTitle(str), "encoding");
            StringBuilder builder = new StringBuilder(new String(fileData, encoding));
            deleteTextByTagNameAndID(builder, "sql", "Base_Column_List");
            insertTextByTagNameAndID(builder, "sql", "Base_Column_List", getSqlText(columnInfoList));

            deleteTextByTagNameAndID(builder, "resultMap", "BaseResultMap");
            insertTextByTagNameAndID(builder, "resultMap", "BaseResultMap", getResultMapText(columnInfoList, primaryKey));

//            deleteTextByTagNameAndID(builder, "select", "selectByPrimaryKey");
//            insertTextByTagNameAndID(builder, "select", "selectByPrimaryKey", getSelectText(primaryKey, tableName));
//
//            deleteTextByTagNameAndID(builder, "delete", "deleteByPrimaryKey");
//            insertTextByTagNameAndID(builder, "delete", "deleteByPrimaryKey", getDeleteText(primaryKey, tableName));
//
//            deleteTextByTagNameAndID(builder, "insert", "insert");
//            insertTextByTagNameAndID(builder, "insert", "insert", getInsertText(columnInfoList, tableName));
//
//            deleteTextByTagNameAndID(builder, "insert", "insertSelective");
//            insertTextByTagNameAndID(builder, "insert", "insertSelective", getInsertSelectiveText(columnInfoList, tableName));
//
//            deleteTextByTagNameAndID(builder, "update", "updateByPrimaryKeySelective");
//            insertTextByTagNameAndID(builder, "update", "updateByPrimaryKeySelective", getUpdateSelectiveText(primaryKey, columnInfoList, tableName));
//
//            deleteTextByTagNameAndID(builder, "update", "updateByPrimaryKey");
//            insertTextByTagNameAndID(builder, "update", "updateByPrimaryKey", getUpdateText(primaryKey, columnInfoList, tableName));

            deleteTextByTagNameAndID(builder, "insert", "batchInsert");
            insertTextByTagNameAndID(builder, "insert", "batchInsert", getBatchInsert(primaryKey, columnInfoList, tableName));

            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            fileOutputStream.write(builder.toString().getBytes(encoding));
            fileOutputStream.close();
        } else {

            String title = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
            String encoding = getAttrValue(title, "encoding");
            Map<String, String> primaryKey = getPrimaryKey(metaData, tableName);
            List<Map<String, String>> columnInfoList = getColumnInfoList(conn,metaData, tableName);

            StringBuilder builder = new StringBuilder(title);
            builder.append("\r\n<mapper namespace=\"");
            builder.append(PROXY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)) + "Mapper");
            builder.append("\">\r\n");
//            builder.append("\t<resultMap id=\"BaseResultMap\" type=\"");
//            builder.append(ENTITY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)));
//            builder.append("\">");
//            //添加resultMap中的text
//            builder.append(getResultMapText(columnInfoList, primaryKey));
//            builder.append("</resultMap>");

//            builder.append("\r\n\t<sql id=\"Base_Column_List\">");
//            //添加sql中的text
//            builder.append(getSqlText(columnInfoList));
//            builder.append("</sql>");

//            builder.append("\r\n\t<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"");
//            builder.append(fullNameOfClass(primaryKey.get("javaType")));
//            builder.append("\">");
//            //添加select中的text
//            builder.append(getSelectText(primaryKey, tableName));
//            builder.append("</select>");

//            builder.append("\r\n\t<delete id=\"deleteByPrimaryKey\" parameterType=\"");
//            builder.append(fullNameOfClass(primaryKey.get("javaType")));
//            builder.append("\">");
//            //添加删除中的text
//            builder.append(getDeleteText(primaryKey, tableName));
//            builder.append("</delete>");
//
//            builder.append("\r\n\t<insert id=\"insert\" parameterType=\"");
//            builder.append(ENTITY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)));
//            builder.append("\">");
//            //添加插入中的text
//            builder.append(getInsertText(columnInfoList, tableName));
//            builder.append("</insert>");

//            builder.append("\r\n\t<insert id=\"insertSelective\" parameterType=\"");
//            builder.append(ENTITY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)));
//            builder.append("\">");
//            //添加插入(selected)中的text
//            builder.append(getInsertSelectiveText(columnInfoList, tableName));
//            builder.append("</insert>");
//
//            builder.append("\r\n\t<update id=\"updateByPrimaryKeySelective\" parameterType=\"");
//            builder.append(ENTITY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)));
//            builder.append("\">");
//            //添加修改(selected)中的text
//            builder.append(getUpdateSelectiveText(primaryKey, columnInfoList, tableName));
//            builder.append("</update>");
//
//            builder.append("\r\n\t<update id=\"updateByPrimaryKey\" parameterType=\"");
//            builder.append(ENTITY_PAGE_NAME + "." + firstUppercase(underlineToHump(tableName)));
//            builder.append("\">");
//            //添加修改(selected)中的text
//            builder.append(getUpdateText(primaryKey, columnInfoList, tableName));
//            builder.append("</update>");

            builder.append("\r\n\t<insert id=\"batchInsert\" useGeneratedKeys=\"true\" keyProperty=\"" + primaryKey.get("javaColumnName") + "\" parameterType=\"java.util.List\">");
            builder.append(getBatchInsert(primaryKey, columnInfoList, tableName));
            builder.append("</insert>");

            builder.append("\r\n</mapper>");
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            fileOutputStream.write(builder.toString().getBytes(encoding));
            fileOutputStream.close();
        }
        System.out.println(firstUppercase(underlineToHump(tableName)) + "Mapper.xml文件生成完成，耗时" + (System.currentTimeMillis() - s) + "毫秒！");
    }

    /**
     * 生成proxy接口文件
     *
     * @param metaData
     * @param tableName
     * @throws IOException
     * @throws SQLException
     */
    private static void generateProxyFiles(Connection conn,DatabaseMetaData metaData, String tableName) throws IOException, SQLException {
        if (PROXY_FILE_PATH == null) return;
        long s = System.currentTimeMillis();
//        Map<String, String> primaryKey = getPrimaryKey(metaData, tableName);
        File dir = new File(PROXY_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, firstUppercase(underlineToHump(tableName)) + "Mapper.java");
//        if (file.exists()) {
//            System.out.println(firstUppercase(underlineToHump(tableName)) + "Mapper.java文件已存在，不再生成！");
//            return;
//        }
        FileOutputStream fos = new FileOutputStream(file);
        StringBuilder builder = new StringBuilder();
        builder.append("package ");
        builder.append(PROXY_PAGE_NAME);
        builder.append(";\r\n\r\nimport ");
        builder.append(ENTITY_PAGE_NAME);
        builder.append(".");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append(";");
        builder.append("\r\ncom.baomidou.mybatisplus.core.mapper.BaseMapper;");
        builder.append("\r\nimport org.springframework.stereotype.Repository;");
        builder.append("\r\nimport java.util.List;\r\n\r\n");

        builder.append("@Repository\r\n");
        builder.append("public interface ");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append("Mapper extends BaseMapper<");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append(">{\r\n\r\n");

//        builder.append("\t/**根据主键删除一条数据*/\r\n\t");
//        builder.append("int deleteByPrimaryKey(");
//        builder.append(primaryKey.get("javaType"));
//        builder.append(" ");
//        builder.append(primaryKey.get("javaColumnName"));
//        builder.append(");\r\n\r\n");

//        builder.append("\t/**新增一条数据*/\r\n\t");
//        builder.append("int insert(");
//        builder.append(firstUppercase(underlineToHump(tableName)));
//        builder.append(" ");
//        builder.append(underlineToHump(tableName));
//        builder.append(");\r\n\r\n");

//        builder.append("\t/**新增一条数据（只赋值有值的字段，null值字段不赋值）*/\r\n\t");
//        builder.append("int insertSelective(");
//        builder.append(firstUppercase(underlineToHump(tableName)));
//        builder.append(" ");
//        builder.append(underlineToHump(tableName));
//        builder.append(");\r\n\r\n");

//        builder.append("\t/**根据主键查询数据*/\r\n\t");
//        builder.append(firstUppercase(underlineToHump(tableName)));
//        builder.append(" selectByPrimaryKey(");
//        builder.append(primaryKey.get("javaType"));
//        builder.append(" ");
//        builder.append(primaryKey.get("javaColumnName"));
//        builder.append(");\r\n\r\n");

//        builder.append("\t/**根据主键修改数据中的字段*/\r\n\t");
//        builder.append("int updateByPrimaryKey(");
//        builder.append(firstUppercase(underlineToHump(tableName)));
//        builder.append(" ");
//        builder.append(underlineToHump(tableName));
//        builder.append(");\r\n\r\n");

//        builder.append("\t/**根据主键修改数据中的字段（只修改有值的字段，null值字段不做修改）*/\r\n\t");
//        builder.append("int updateByPrimaryKeySelective(");
//        builder.append(firstUppercase(underlineToHump(tableName)));
//        builder.append(" ");
//        builder.append(underlineToHump(tableName));
//        builder.append(");\r\n\r\n");

        builder.append("\t/**批量新增数据*/\r\n\t");
        builder.append("int batchInsert(List<");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append("> list);\r\n\r\n");

        builder.append("}");
        fos.write(builder.toString().getBytes(FILE_CHAR_SET));
        fos.close();
        long e = System.currentTimeMillis();
        System.out.println((firstUppercase(underlineToHump(tableName))) + "Mapper接口文件生成完成，耗时" + (e - s) + "毫秒！");
    }


    /**
     * 生成实体类的文件
     */
    private static void generateEntityFiles(Connection conn,DatabaseMetaData metaData, String tableName, String comment) throws IOException, SQLException {
        if (ENTITY_FILE_PATH == null) return;
        long s = System.currentTimeMillis();
        File dir = new File(ENTITY_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, firstUppercase(underlineToHump(tableName)) + ".java");
        FileOutputStream fos = new FileOutputStream(file);
        StringBuilder builder = new StringBuilder();
        builder.append("package ");
        builder.append(ENTITY_PAGE_NAME);
        builder.append(";\r\n\r\n\r\n");

        builder.append("import com.baomidou.mybatisplus.annotation.TableId;\r\n");
        builder.append("import com.baomidou.mybatisplus.annotation.TableName;\r\n");
        builder.append("import com.baomidou.mybatisplus.annotation.TableField;\r\n");
        builder.append("import com.baomidou.mybatisplus.annotation.IdType;\r\n");
        builder.append("\r\n\r\n");


        builder.append("@TableName(\"");
        builder.append(tableName);
        builder.append("\")");
        builder.append("public class ");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append(" {\r\n\r\n");
        Map<String, String> primaryKey = getPrimaryKey(metaData, tableName);
        List<Map<String, String>> columnInfoList = getColumnInfoList(conn,metaData, tableName);

        for (Map<String, String> columnInfo : columnInfoList) {
            String remark = columnInfo.get("remark");
            String javaColumnName = columnInfo.get("javaColumnName");
            String type = columnInfo.get("javaType");
            if (remark != null) {
                builder.append("\t/**");
                builder.append(remark);
                builder.append("*/");
                builder.append("\r\n");
            }
            if (javaColumnName.equals(primaryKey.get("javaColumnName"))) {
                builder.append("\t@TableId(value = \"");
            } else {
                builder.append("\t@TableField( value = \"");
            }
            builder.append(columnInfo.get("dataColumnName"));

            if (javaColumnName.equals(primaryKey.get("javaColumnName"))) {

                if (primaryKey.get("autoIncrement").equals("true")) {
                    builder.append("\", type = IdType.AUTO )\r\n");
                } else {
                    builder.append("\", type = IdType.INPUT )\r\n");
                }

            } else {

                builder.append("\")\r\n");
            }
            builder.append("\tprivate ");

            String filedStr = "import com.baomidou.mybatisplus.annotations.TableField;\r\n";
            if (type == "Date") {
                int index = builder.indexOf(filedStr);
                if (builder.indexOf("java.util.Date") == -1) {
                    builder.insert(index + filedStr.length(), "import java.util.Date;\r\n");
                }
            } else if (type == "BigDecimal") {
                int index = builder.indexOf(filedStr);
                if (builder.indexOf("java.math.BigDecimal") == -1) {
                    builder.insert(index + filedStr.length(), "import java.math.BigDecimal;\r\n");
                }
            }


            //生成字段
            builder.append(type);
            builder.append(" ");
            builder.append(javaColumnName);
            builder.append(";");
            builder.append("\r\n\r\n");

        }
        //生成get和set方法
        for (Map<String, String> columnInfo : columnInfoList) {
            String javaColumnName = columnInfo.get("javaColumnName");
            String type = columnInfo.get("javaType");

            //生成get方法
            builder.append("\tpublic ");
            builder.append(type);
            builder.append(" get");
            builder.append(firstUppercase(javaColumnName));
            builder.append("(){\r\n\t\t");
            builder.append("return this.");
            builder.append(javaColumnName);
            builder.append(";\r\n\t}\r\n");


            //生成set方法
            builder.append("\tpublic void");
            builder.append(" set");
            builder.append(firstUppercase(javaColumnName));
            builder.append('(');
            builder.append(type);
            builder.append(' ');
            builder.append(javaColumnName);
            builder.append("){\r\n\t\tthis.");
            builder.append(javaColumnName);
            builder.append(" = ");
            builder.append(javaColumnName);
            builder.append(";\r\n\t}\r\n\r\n");

        }

        if (builder.indexOf("import") != -1) {
            int index = builder.indexOf("public class");
            builder.insert(index, "\r\n");
        }


        builder.append("\r\n\t@Override\r\n\tpublic String toString() {\r\n\t\treturn \"");
        builder.append(firstUppercase(underlineToHump(tableName)));
        builder.append("{\" +\r\n");

        boolean first = true;
        for (Map<String, String> columnInfo : columnInfoList) {
            if (first) {
                first = false;
                builder.append("\t\t\t\t\"");
            } else {
                builder.append("\t\t\t\t\", ");
            }

            builder.append(columnInfo.get("javaColumnName"));
            builder.append("=\" + ");
            builder.append(columnInfo.get("javaColumnName"));
            builder.append(" +\r\n");
        }

        if (comment != null && comment.trim().length() > 0) {
            int index = builder.indexOf("@TableName");
            builder.insert(index, "/**\r\n *" + comment + "实体类\r\n */\r\n");
        }
        builder.append("\t\t\t\t\"}\";\r\n");
        builder.append("\t}\r\n");
        builder.append('}');
        fos.write(builder.toString().getBytes(FILE_CHAR_SET));
        fos.close();
        long e = System.currentTimeMillis();
        System.out.println((firstUppercase(underlineToHump(tableName))) + "实体类生成完成，耗时" + (e - s) + "毫秒！");
    }


    private static List<Map<String, String>> getColumnInfoList(Connection conn,DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Map<String, String>> columnInfoList = new ArrayList<>();
        ResultSet columns = metaData.getColumns(conn.getCatalog(), URL, tableName, null);


        while (columns.next()) {
            Map<String, String> map = new HashMap<>();
            map.put("javaColumnName", underlineToHump(columns.getString("COLUMN_NAME")));
            map.put("dataColumnName", columns.getString("COLUMN_NAME"));
            map.put("jdbcType", toJdbcType(columns.getString("TYPE_NAME")));
            map.put("javaType", toJavaType(columns.getString("TYPE_NAME")));
            map.put("remark", columns.getString("REMARKS"));
            columnInfoList.add(map);
        }
        return columnInfoList;
    }


    private static String toJdbcType(String str) {
        switch (str) {
            case "INT":
                str = "INTEGER";
                break;
            case "DATETIME":
                str = "TIMESTAMP";
                break;
        }
        return str;
    }

    /**
     * 将数据库中对应的类型转换为Java中对应的类型
     *
     * @param typeName
     * @return
     */
    private static String toJavaType(String typeName) {
        switch (typeName) {
            case "BIT":
            case "TINYINT":
                return "Byte";
            case "SMALLINT":
                return "Short";
            case "MEDIUMINT":
            case "INT":
            case "INT UNSIGNED":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "FLOAT":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "DATE":
            case "TIME":
            case "TIMESTAMP":
            case "YEAR":
            case "DATETIME":
                return "Date";
            case "DECIMAL":
                return "BigDecimal";
            case "CHAR":
            case "TINYTEXT":
            case "TEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
            case "VARCHAR":
            case "VARCHAR2":
            case "BLOB":
            case "JSON":
                return "String";
        }
        System.out.println(typeName);
        return "String";
    }

    /**
     * 获取主键的名字和类型
     *
     * @param metaData
     * @param tableName
     * @return
     * @throws SQLException
     */
    private static Map<String, String> getPrimaryKey(DatabaseMetaData metaData, String tableName) throws SQLException {
        ResultSet resultSet = metaData.getPrimaryKeys(null, null, tableName);
        ResultSet columns = metaData.getColumns(null, URL, tableName, null);
        Map<String, String> result = new HashMap<>();
        result.put("javaColumnName", "id");
        result.put("javaType", "Integer");
        result.put("jdbcType", "INTEGER");
        result.put("dataColumnName", "id");
        result.put("autoIncrement", "true");

        if (resultSet.next()) {
            result.put("javaColumnName", underlineToHump(resultSet.getString("COLUMN_NAME")));
            result.put("dataColumnName", resultSet.getString("COLUMN_NAME"));
            while (columns.next()) {
                if (columns.getString("COLUMN_NAME").equals(result.get("dataColumnName"))) {
                    if ("VARCHAR".equals(toJdbcType(columns.getString("TYPE_NAME")))) {
                        result.put("autoIncrement", "false");
                    }
                    result.put("javaType", toJavaType(columns.getString("TYPE_NAME")));
                    result.put("jdbcType", toJdbcType(columns.getString("TYPE_NAME")));
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 下划线变驼峰命名
     *
     * @param str
     * @return
     */
    public static String underlineToHump(String str) {
        StringBuilder builder = new StringBuilder();
        str = str.toLowerCase();
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    c = str.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        builder.append((char) (c - 32));
                    } else {
                        builder.append(c);
                    }
                }
            } else {
                builder.append((c));
            }
        }
        return builder.toString();
    }


    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstUppercase(String str) {
        return ((char) (str.charAt(0) - 32)) + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String firstLowercase(String str) {
        return ((char) (str.charAt(0) + 32)) + str.substring(1);
    }

    public static String fullNameOfClass(String str) {
        switch (str) {
            case "Byte":
            case "Short":
            case "Integer":
            case "Long":
            case "Float":
            case "Double":
            case "Character":
            case "String":
                return "java.lang." + str;
            case "Date":
                return "java.util.Date";
            case "BigDecimal":
                return "java.math.BigDecimal";
        }
        return str;
    }

    /**
     * 驼峰命名变下滑线命名
     *
     * @param str
     * @return
     */
    public static String humpToUnderline(String str) {
        StringBuilder builder = new StringBuilder();
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                builder.append('_');
                builder.append((char) (c + 32));
            } else {
                builder.append((c));
            }
        }
        return builder.toString();
    }

}
