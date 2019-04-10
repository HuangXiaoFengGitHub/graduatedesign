package com.example.graduatedesign;


import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class GraduatedesignApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(GraduatedesignApplication.class, args);
        String sb="hxf";
        log.info("日志框架："+sb);
        // String id= RegService
    }
//    @Autowired
//    private LoginMapper loginMapper;
    //hibernate5组件
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    //Commons-Multipart配置
    @Bean(name="commonsMultipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() {
        final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(-1);
        return commonsMultipartResolver;
    }
    @Bean
    public FilterRegistrationBean multipartFilterRegistrationBean() {
        final MultipartFilter multipartFilter = new MultipartFilter();
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
        filterRegistrationBean.addInitParameter("multipartResolverBeanName", "commonsMultipartResolver");
        return filterRegistrationBean;
    }
    //显示声明CommonsMultipartResolver为mutipartResolver
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(5 * 1024 * 1024);//上传文件大小 5M 5*1024*1024
        return resolver;
    }

    //增加json缩进，提高可读性
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
//        return builder -> builder.indentOutput(true);
//    }
    public void run(ApplicationArguments args) throws Exception
    {
        log.info("begin:");
        multipartResolver();
        //generateArtifacts(); //生成
        //      playWithArtifacts(); //演示
    }
    private void generateArtifacts() throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                this.getClass().getResourceAsStream("/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

//    private void playWithArtifacts() {
//        Login login=Login.builder().name("hxf").phone("135388e1957").build();
//        Login login2=Login.builder().name("hxf").phone("13538d61957").build();
//        log.info(login.toString());
//        log.info(login2.toString());
//        loginMapper.insert(login2);//插入
//        loginMapper.insert(login);//插入
//        Login s = loginMapper.selectByPrimaryKey(1L);//查询
//        log.info("logio {}", s);
//        LoginExample example = new LoginExample();
//        example.createCriteria().andNameEqualTo("hxf");
//
//        example.setOrderByClause("id desc");
//        List<Login> list = loginMapper.selectByExample(example);//查询
//        list.forEach(e -> log.info("selectByExample: {}", e));
//    }
}
