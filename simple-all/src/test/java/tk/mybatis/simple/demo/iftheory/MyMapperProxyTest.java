package tk.mybatis.simple.demo.iftheory;

import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tk.mybatis.simple.mapper.BaseMapperTest;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.model.SysUser;

/*
 * 2.8小节的示例代码
 * */
public class MyMapperProxyTest extends BaseMapperTest {

  @Test
  public void testDemoIftheory() {
    SqlSession sqlSession = getSqlSession();

    MyMapperProxy userMapperProxy = new MyMapperProxy(UserMapper.class, sqlSession);
    UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(
        Thread.currentThread().getContextClassLoader(),
        new Class[] { UserMapper.class },
        userMapperProxy);
    // 调用 selectAll 方法
    List<SysUser> user = userMapper.selectAll();
  }

}

