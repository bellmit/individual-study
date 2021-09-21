package cn.gyw.thirdpart.cli;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * CLI即Command Line Interface ， 命令行接口 ，它为Java程序访问和解析命令行参数提供了一种统一的接口。
 * 它在解释命令行时主要有三个状态，即：定义、解释和询问交互。
 * 通过使用commons cli则可以很容易的访问参数，而不必去循环String[] args。
 * @author guanyw
 *
 */
public class EasyCliDemo {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        options.addOption("t",true,"display current time");
        options.addOption("p", true , "person owner");
        options.addOption("h",false,"help");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption("p")){
            String code = cmd.getOptionValue("p");
            System.out.println("输入参数 P: " + code);
        }

        if(cmd.hasOption("t")){
            String code = cmd.getOptionValue("t");
            System.out.println("输入参数 t: " + code + " , "+new Date());
        }

        if(cmd.hasOption("h")){
            System.out.println("输入参数h:帮助：[-t][-c][-h] ");
        }
    }
}
