// Importation des classes nécessaires
package mg.inclusiv.mihary.configuration;

import mg.inclusiv.mihary.filter.MyCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // Indique que cette classe est un composant Spring géré par conteneur d'inversion de contrôle
public class CorsInterceptor implements HandlerInterceptor {

    @Autowired
    private MyCorsFilter corsFilter;

    @Bean
    public FilterRegistrationBean<MyCorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<MyCorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(corsFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
