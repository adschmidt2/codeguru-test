package my.codeguru.example.codegurutest.controllers;


import com.sun.xml.internal.ws.util.CompletedFuture;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class TestController {

    @Resource
    TaskExecutor taskExecutor;

    @RequestMapping("/test")
    public String index() {

        concurrency();

        return "Up";
    }


    private void concurrency() {

        List<String> lst = new ArrayList<String>();
        IntStream.range(0,100).forEach(i -> lst.add(String.valueOf(i)));

        CompletableFuture<List<String>> future = CompletableFuture.completedFuture(lst);

        Supplier<String> task = () -> {
            try {
                return future.get().stream().map(i -> {
                    try {
                        Thread.sleep(1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i + "-";
                }).collect(Collectors.joining());

            } catch (InterruptedException e) {
                e.printStackTrace();
                return "";
            } catch (ExecutionException e) {
                e.printStackTrace();
                return "";
            }
        };

        CompletableFuture<String> result = CompletableFuture.supplyAsync(task, taskExecutor);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lst.add("A");

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
