package parking.jpa.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import jpa.Jpa
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(EnableDataSourceConfiguration::class)
@EnableJpaRepositories(
    entityManagerFactoryRef = "parKingEntityManagerFactory",
    transactionManagerRef = "parKingTransactionManager",
    basePackageClasses = [Jpa::class]
)
@EnableTransactionManagement
@EnableJpaAuditing
class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "parking.datasource")
    fun parKingDataSourceProperty() : HikariConfig {
       return HikariConfig()
    }

    @Bean
    fun parKingDataSource(): DataSource {
        val data = HikariDataSource(parKingDataSourceProperty())
        return LazyConnectionDataSourceProxy(data)
    }

    @Bean
    fun parKingEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        val properties = HashMap<String, String>()
        properties[AvailableSettings.USE_SECOND_LEVEL_CACHE] = "false"
        properties[AvailableSettings.USE_QUERY_CACHE] = "false"

        return builder.dataSource(parKingDataSource()).packages(Jpa::class.java).properties(properties).persistenceUnit("parKing").build()
    }

    @Primary
    @Bean("parKingTransactionManager")
    fun parKingTransactionManager(@Qualifier("parKingEntityManagerFactory") factory: EntityManagerFactory) = JpaTransactionManager(factory)
}