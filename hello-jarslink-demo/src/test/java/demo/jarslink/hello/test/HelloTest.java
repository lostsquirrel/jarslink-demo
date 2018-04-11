package demo.jarslink.hello.test;

import com.alipay.jarslink.api.Module;
import com.alipay.jarslink.api.ModuleConfig;
import com.alipay.jarslink.api.ModuleLoader;
import com.alipay.jarslink.api.ModuleManager;
import com.alipay.jarslink.api.impl.ModuleManagerImpl;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import demo.jarslink.hello.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class HelloTest {

    @Autowired
    ModuleManager moduleManager;

    @Autowired
    ModuleLoader moduleLoader;

    @Test
    public void testHello() {
        //1:加载模块
        Module module = moduleLoader.load(buildModuleConfig());

        //2:注册模块
        ModuleManager moduleManager = new ModuleManagerImpl();
        moduleManager.register(module);

        //查找模块
        Module findModule = moduleManager.find(module.getName());
        Assert.assertNotNull(findModule);

//查找和执行Action
        String actionName = "helloworld";
        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setName("h");
        moduleConfig.setEnabled(true);
        ModuleConfig result = findModule.doAction(actionName, moduleConfig);
    }

    public static ModuleConfig buildModuleConfig() {
//        URL demoModule = Thread.currentThread().getContextClassLoader().getResource("META-INF/spring/demo-1.0.0.jar");
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/"));
        URL demoModule = Thread.currentThread().getContextClassLoader().getResource("jarslink-demo-1.0-SNAPSHOT.jar");
        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setName("demo");
        moduleConfig.setEnabled(true);
        moduleConfig.setVersion("1.0.0.20170621");
        moduleConfig.setProperties(ImmutableMap.of("svnPath", new Object()));
        moduleConfig.setModuleUrl(ImmutableList.of(demoModule));
        return moduleConfig;
    }
}
