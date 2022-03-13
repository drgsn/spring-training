package com.training;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.Size;

/**
 * two simple commands, one to grab the contents of an URL and display them, and other to save those contents to a file:
 */
@ShellComponent
public class SimpleCli {

    @ShellMethod("add two integers")
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod(value = "add two integers", key = "sum")
    public int sumCommand(int a, int b) {
        return a + b;
    }

    /**
     * shell:>echo 1 2 3
     * You said a=1, b=2, c=3
     * <p>
     * shell:>echo --a 1 --b 2 --c 3
     * You said a=1, b=2, c=3
     * <p>
     * shell:>echo --b 2 --c 3 --a 1
     * You said a=1, b=2, c=3
     * <p>
     * shell:>echo --a 1 2 3
     * You said a=1, b=2, c=3
     * <p>
     * shell:>echo 1 --c 3 2
     * You said a=1, b=2, c=3
     */
    @ShellMethod("Display stuff.")
    public String echo(int a, int b, int c) {
        return String.format("You said a=%d, b=%d, c=%d", a, b, c);
    }

    @ShellMethod("Say hello.")
    public String greet(@ShellOption(defaultValue = "World") String who) {
        return "Hello " + who;
    }

//    /**
//     * shell:>add 1 2 3.3
//     * 6.3
//     * shell:>add --numbers 1 2 3.3
//     * 6.3
//     */
//    @ShellMethod("Add Numbers.")
//    public float add(@ShellOption(arity=3) float[] numbers) {
//        return numbers[0] + numbers[1] + numbers[2];
//    }

    /**
     * shell:>change-password hello
     * The following constraints were not met:
     * 	--password string : size must be between 8 and 40 (You passed 'hello')
     */
    @ShellMethod("Change password.")
    public String changePassword(@Size(min = 8, max = 40) String password) {
        return "Password successfully set to " + password;
    }

}
