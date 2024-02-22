package parking.jpa.configuration

import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@MustBeDocumented
@Inherited
@Import(DataSourceConfiguration::class)
annotation class EnableDataSourceConfiguration
