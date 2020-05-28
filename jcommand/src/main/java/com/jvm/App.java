package com.jvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Hello world!
 */
public class App {
    @Parameter(names = {"--length", "-l"}, help = true, required = true, description = "this is length info")
    int length;
    @Parameter(names = {"--pattern", "-p"}, help = true, required = true, description = "this is pattern info")
    int pattern;
    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    public static void main(String[] args) {
        App main = new App();
        JCommander jct = JCommander.newBuilder()
                .addObject(main)
                .build();
        jct.setProgramName("demo app");
            jct.parse(args);
            if (main.help) {
                jct.usage();
                return;
            }
            main.run();
    }

    public void run() {
        System.out.printf("%d %d", length, pattern);
    }
}
