package com.tianshuo.springshell.command;

import com.tianshuo.springshell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup("calculate")
public class CalculateTask {

    @Autowired
    private UserService userService;

    /**
     * addition | sum 命令均可
     * <ul>
     *     <li>
     *         shell:> addition 1 2
     *     </li>
     *     <li>
     *         shell:> addition --x 1 --y 2     使用带命令参数前缀的方式
     *     </li>
     *     <li>
     *         shell:> addition --y 1 --x 2
     *     </li>
     *     <li>
     *         shell:> sum --y 1 --x 2
     *     </li>
     * </ul>
     * */
    @ShellMethod(key = {"addition", "sum"}, value = "加法运算")
    private void addition(int x, int y) {
        System.out.println("计算两数相加, x="+x+", y="+y);
    }



    /**
     * 使用属性 {@link ShellMethod#prefix()} 定义参数前缀
     *
     * <ul>
     *     <li>
     *         shell:> subtraction 1 2
     *     </li>
     *     <li>
     *         shell:> subtraction @x 2 @y 3
     *     </li>
     *     <li>
     *         shell:> subtraction @y 2 @x 3
     *     </li>
     * </ul>
     * */
    @ShellMethod(key = "subtraction", value = "加法运算", prefix = "@")
    private void subtraction(int x, int y) {
        System.out.println("计算两数相减, x="+x+", y="+y);
    }



    /**
     * 使用注解 @ShellOption 对命令参数进行定制
     *
     * <ul>
     *     <li>
     *         shell:> multiplication 1 2
     *     </li>
     *     <li>
     *         shell:> multiplication -a 3 -b 5
     *     </li>
     *     <li>
     *         shell:> multiplication -b 3 -a 5
     *     </li>
     * </ul>
     * */
    @ShellMethod(key = "multiplication", value = "乘法运算")
    private void multiplication(@ShellOption("-a") int x, @ShellOption("-b") int y) {
        System.out.println("计算两数相乘, x="+x+", y="+y);
    }



    /**
     * 使用注解 @ShellOption 为命令参数指定多个名称
     *
     * <ul>
     *     <li>
     *         shell:> division 3 5
     *     </li>
     *     <li>
     *         shell:> division -m 3 @n 5
     *     </li>
     *     <li>
     *         shell:> division @m 3 @n 5
     *     </li>
     *     <li>
     *         shell:> division @m 3 -n
     *     </li>
     * </ul>
     * */
    @ShellMethod(key = "division", value = "除法运算")
    private void division(@ShellOption({"@m", "-m"}) int x, @ShellOption({"@n", "-n"}) int y) {
        System.out.println("计算两数相除, x="+x+", y="+y);
    }

    @ShellMethod(key = "service", value = "服务调用")
    private void servic(int a) {
        System.out.println("调用服务方法！");
        userService.show(a);
    }

}
