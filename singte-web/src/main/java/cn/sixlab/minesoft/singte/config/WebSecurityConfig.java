package cn.sixlab.minesoft.singte.config;

import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.service.StUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StLoginStatusFilter loginStatusFilter;
    @Autowired
    private StAuthenticationHandler authenticationHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, StUserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();

        httpSecurity.headers().frameOptions().sameOrigin().cacheControl();

        httpSecurity.csrf().disable();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler);

        // dont authenticate this particular request
        httpSecurity.authorizeRequests()
                .antMatchers("/**/login/**").permitAll()
                .antMatchers("/user/**").hasAnyAuthority(StConst.ROLE_USER, StConst.ROLE_ADMIN)
                .antMatchers("/admin/**").hasAuthority(StConst.ROLE_ADMIN)
                .anyRequest().permitAll();

        httpSecurity.addFilterBefore(loginStatusFilter, UsernamePasswordAuthenticationFilter.class);

        //添加 校验过滤器
        //        Filter authenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        //        httpSecurity.addFilter(authenticationFilter);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/favicon.ico",
                "/static/**",
                "/MP_verify_*.txt",
                "/file/files/*"
        );
    }
}
