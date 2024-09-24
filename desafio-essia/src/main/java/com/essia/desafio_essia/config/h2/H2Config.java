package com.essia.desafio_essia.config.h2;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.essia.desafio_essia.domain.repository.h2",
        transactionManagerRef = "h2TransactionManager"
)
@EnableTransactionManagement
public class H2Config {

    @Bean(name = "h2")
    @ConfigurationProperties(prefix = "spring.h2")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:essia")
                .username("admin")
                .password("")
                .build();
    }

    @Bean(name = "h2TransactionManager")
    public JpaTransactionManager h2TransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) throws Exception {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
