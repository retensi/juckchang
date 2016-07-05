package bella.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan("bella.mapper")
@PropertySource(value = { "classpath:db.properties" })
public class DBConfig { 

	@Autowired
    private Environment environment;
	
	@Bean
    public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        
        return dataSource;
    }
     
    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
     
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:bella/sql/*.xml"));
        sessionFactory.setTypeAliasesPackage("bella.vo");
        return sessionFactory;
    }
    
}
