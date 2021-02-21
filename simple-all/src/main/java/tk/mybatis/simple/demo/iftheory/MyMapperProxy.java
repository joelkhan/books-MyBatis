package tk.mybatis.simple.demo.iftheory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

/*
 * 2.8小节的示例代码
 * */
public class MyMapperProxy<T> implements InvocationHandler {
  private Class<T> mapperinterface;
  private SqlSession sqlSession;
  
  public MyMapperProxy(Class<T> mapperinterface, SqlSession sqlSession) {
    this.mapperinterface = mapperinterface;
    this.sqlSession = sqlSession;
  }
  
  @Override
  public Object invoke(Object proxy , Method method, Object[] args) throws Throwable {
    System.out.println("MyMapperProxy begin >>>\n  namespace + methodId = " + 
        (mapperinterface.getCanonicalName() + "." + method.getName()));
    // 针对不同的 sql 类型，需要调用 sqlSession 不同的方法
    // 接口方法中的参数也有很多情况 ，这里只考虑没有参数的情况
    List<T> list= sqlSession.selectList(mapperinterface.getCanonicalName() + "." + 
        method.getName());
    System.out.println("MyMapperProxy end >>>");
    // 返回值也有很多情况，这里不做处理直接返回
    return list;
  }
  
}

