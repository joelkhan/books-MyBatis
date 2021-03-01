package tk.mybatis.simple.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import tk.mybatis.simple.model.Country;

public class CountryMapperTest {
  private static Logger logger = Logger.getLogger(CountryMapperTest.class);
  
  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void init() {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      reader.close();
    } catch (IOException ignore) {
      ignore.printStackTrace();
    }
  }

  @Test
  public void testSelectAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      List<Country> countryList = sqlSession.selectList("selectAll");
      printCountryList(countryList);
    } finally {
      sqlSession.close(); // 不要忘记关闭！
    }
  }

  private void printCountryList(List<Country> countryList) {
    for (Country country : countryList) {
      // 使用logger输出日志
      logger.info("one line country name: " + country.getCountryname());
      System.out.printf("%-4d%-8s%4s\n", 
        country.getId(), country.getCountryname(), country.getCountrycode());
    }
  }
  
}

