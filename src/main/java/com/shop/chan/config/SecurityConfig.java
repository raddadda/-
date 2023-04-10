package com.shop.chan.config;


import com.shop.chan.service.JoinService;
import com.shop.chan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //빈 등록(Ioc관리)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    MemberService memberService;
  //  JoinService joinService;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //super.configure(http); // 기존 시큐리티가 가진 모든 기능 비활성화
        //http.csrf().disable(); // csrf 토큰 비활성화
/*
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()
                .and()
                .csrf().ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**"));
*/

        http.authorizeHttpRequests()
                //허용할 요청
                .requestMatchers( "/main/**").authenticated() // 이 주소에서는 인증이 필요
              //  .requestMatchers("/main/**","/signup/**,","/signin/**").permitAll()
               // .requestMatchers("/css/**","/js/**","/images/**").permitAll()
              //  .requestMatchers("/","/members/**","/item/**","/itemes/**").permitAll()
               // .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // 그외에것들 모든 주소에서 인증이 필요 없다
                .and()
                .formLogin()
                .loginPage("/signin") // 인증필요한 주소로 접속하면 이 주소로 이동시킴
                .loginProcessingUrl("/signin") // 스프링 시큐리티가 로그인 자동 진행
                .defaultSuccessUrl("/main"); // 로그인이 정상적이면 "/" 로 이동
        //예외 발생시 핸들링
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            //http.csrf().disable();
            http.formLogin()
                    .loginPage("/members/login") // 로그인페이지
                    .defaultSuccessUrl("/") // 로그인 성공 페이지
                    .usernameParameter("email")// 로그인시 사용할 파라미터
                    .failureUrl("/members/login/error")//로그인 실패시 이동할 페이지
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    .logoutSuccessUrl("/"); //로그아웃 성공 페이지
        //auth는 스프링 시큐리티 컨테스트 내에 존재하는 인증절차를 거쳐 통과해야함.(실패시 401 에러)
           //401 에러가 났을때 처리해주는 로직을 'authenticationEntryPoint' 라고 한다.
            http.authorizeHttpRequests()
                    .requestMatchers("/css/**","/js/**","/images/**").permitAll()  //허용할 요청
                    .requestMatchers("/","/members/**","/items/**","/itemes/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated(); // 나머지는 인증이 필요


            http.exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }*/

/*    @Bean
    public BCryptPasswordEncoder encoder() {
        // DB 패스워드 암호화
        return new BCryptPasswordEncoder();
    }*/
/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
   @Bean
   public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }
}
