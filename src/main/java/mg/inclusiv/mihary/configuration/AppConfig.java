package mg.inclusiv.mihary.configuration;

import mg.inclusiv.mihary.filter.MyCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<MyCorsFilter> corsFilter() {
        FilterRegistrationBean<MyCorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyCorsFilter());
        registrationBean.setOrder(0);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
