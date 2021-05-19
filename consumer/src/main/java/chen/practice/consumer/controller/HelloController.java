package chen.practice.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import chen.practice.common.service.IHelloService;

import java.sql.*;


@RestController
public class HelloController {
    @DubboReference
    private IHelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        log.info("hello");
        return helloService.hello();
    }
    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1() {
        log.info("hello1");
        return "hello1";
    }

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);


    public static void main(String[] args) {
        Connection conn = null;
        Connection connection = null;
        String sql;
//        String url = "jdbc:mysql://localhost:3306/test?" +
//                "user=root&password=root&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT";
        String url = "jdbc:mysql://192.168.62.17:3306/mas-party?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true";
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            log.info("成功加载MySql驱动程序");
            conn = DriverManager.getConnection(url, user, password);
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            String sql2 = "update temp_member set dept_id = ? where GMSFHM = ?";
            PreparedStatement pstm = connection.prepareStatement(sql2);
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            Statement stmt1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement stmt2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // sql = "create table student(No char(30), name varchar(30), primary key(No))";
//            int result = stmt.executeUpdate(sql);
//            if (result != -1) {
                log.info("创建数据表成功");
//                sql = "insert into student(no, name) values ('201901', 'sui123')";
//                result = stmt.executeUpdate(sql);
//                sql = "insert into student(no, name) values ('201902', 'ya123')";
//                result = stmt.executeUpdate(sql);
                sql = "select id,HJXXDZ,GMSFHM from temp_member where  HJXXDZ like '%自然村%' and YHZGX_N = '户主' ";
                ResultSet rs = stmt.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    Long l = System.currentTimeMillis();
                    Long id =rs.getLong(1);
                    String address = rs.getString(2);
                    String uscc = rs.getString(3);
                    int p1 = address.indexOf("县");
                    int p2 = address.indexOf("村");
                    int p3 = address.lastIndexOf("自然村");

                    if(p2 != p3+2){
                        String xianName = address.substring(p1 + 1,p2 + 1);
                        String villageName =  address.substring(p2 + 1, p3);
                        String sql1 = "select a.dept_id from sys_dept a inner join sys_dept b on a.parent_id = b.dept_id\n" +
                                "where  a.dept_name = '"+ villageName +"' and b.dept_name = '"+ xianName +"'";
                        ResultSet rs1 = stmt1.executeQuery(sql1);
                        rs1.last();
                        int size = rs1.getRow();
                        rs1.beforeFirst();
                        Long deptId = 0L;
                        log.info(" 1rewriteBatchedStatements time:"+(System.currentTimeMillis()-l)+"ms");
                        if(size == 1){
                            while (rs1.next()){
                                deptId =  rs1.getLong(1);
                            }
                            count++;
                            pstm.setLong(1,deptId);
                            pstm.setString(2,uscc);
                            pstm.addBatch();
                            if(count % 6000 == 0){
                                pstm.executeBatch();
                            }
                        }
                        log.info(" 2rewriteBatchedStatements time:"+(System.currentTimeMillis()-l)+"ms");

                        pstm.executeBatch();
                        connection.commit();
                        log.info(" 3rewriteBatchedStatements time:"+(System.currentTimeMillis()-l)+"ms");
                        log.info(id + "   " + xianName + "   " + villageName + "  "+ deptId);
                    }

                }
            log.info("count" + count);
//            }
        } catch (ClassNotFoundException e) {
            log.error("没有找到驱动类");
            throw new RuntimeException("没有找到加载类");
        } catch (SQLException e) {
            log.error("sql不正确", e.getMessage(), e);
            throw new RuntimeException("sql不正确");
        } finally {
            try {
                conn.close();
                connection.close();
            } catch (SQLException e) {
                log.error("关闭连接失败");
                throw new RuntimeException("关闭连接失败");
            }
        }
    }
}
