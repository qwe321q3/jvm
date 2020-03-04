package servlet3;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Set;

/**
 * HandlesTypes 配置感兴趣的class
 *
 * @author tianshuo
 */
@HandlesTypes(value = {UserService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /**
     * @param c 为 HandlesTypes 类配置的class的实现类
     * @param ctx
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("感兴趣的类型：");

        for (Class<?> clazz : c) {
            {
				/**
				 * 判断这个类是不是接口，并且不是抽象类，并且UserService这个类是他的父类
				 */
				if (!clazz.isInterface() && UserService.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
					System.out.println(" =  = == = =  " + clazz);

				}
            }
        }
        ServletRegistration.Dynamic testServlet = ctx.addServlet("google", new GoogleSerlvet());
        testServlet.addMapping("/google");
        ctx.addFilter("secFilter", new GoogleFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        ctx.addListener(new LoadListener());
    }
}
