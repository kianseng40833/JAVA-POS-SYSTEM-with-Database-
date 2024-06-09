import com.Dickson.Mapperdao.AdminMapper;
import com.Dickson.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class Testadmin {

    SqlSessionFactory sqlSessionFactory = null; //必须要写

    @Before
    public void before() throws IOException { //必须要写
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testInsert() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            int affectedRows = mapper.saveAdmin(new Admin(222, "jeerry", "234"));
            log.debug("[{}]", affectedRows);
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            try {
                AdminMapper mapper = session.getMapper(AdminMapper.class);
                int affectedrows = mapper.updateAdmin(new Admin(5, "lucy's husband", "this's password"));
                log.debug("[{}]", affectedrows);
//                int i = 1 / 0;
//                System.out.println(i);
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
                session.rollback();
            }
        }
    }

    @Test
    public void testdelete() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            try {
                AdminMapper mapper = session.getMapper(AdminMapper.class);
                int affectedrows = mapper.deleteAdmin(1);
                log.debug("[{}]", affectedrows);
//                int i = 1 / 0;
//                System.out.println(i);
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
                session.rollback();
            }
        }
    }

    @Test
    public void testFindByID() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            Admin admdin = mapper.findAdminById(5);
            log.debug("[{}]", admdin);
            System.out.println(admdin.getId());
            System.out.println(admdin.getUsername());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            List<Admin> result = mapper.findAllAdmins();
            log.debug("[{}]", result);
        }
    }

    @Test
    public void testGetID() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            Admin admdin = mapper.getAdminById(5);
            log.debug("[{}]", admdin);
            System.out.println(admdin.getId());
            System.out.println(admdin.getUsername());
        }
    }

    @Test
    public void demo() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            List<Admin> admin = mapper.getAdminByUsername("i");
            log.debug("[{}]", admin);
        }
    }

    @Test
    public void demo4() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AdminMapper mapper = session.getMapper(AdminMapper.class);
            List<Admin> admins = mapper.demo3("lucy's husband");
            log.debug("[{}]", admins);
        }
    }
}
