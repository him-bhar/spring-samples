package com.himanshu.poc.spring.samples.springbootwebreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SampleMain {
  private static Logger logger = LoggerFactory.getLogger(SampleMain.class);

  public static void main(String[] args) {
    String[] data = new String[] {"Hello", "World", "Himanshu", "Testing"};
    Supplier<Stream<String>> s = new RandomStringSupplier(data, data.length);

    Flux<String> observable = Flux.fromStream(s::get).subscribeOn(Schedulers.elastic()).publishOn(Schedulers.parallel());//;
    observable.toStream();  //Make it blocking
    observable.next().subscribe(logger::info);
    observable.next().subscribe(logger::info);
    observable.next().subscribe(logger::info);
    observable.next().subscribe(logger::info);
    observable.next().subscribe(logger::info);
    observable.subscribe(System.out::println, t -> System.out.println("Got error "+ t), () -> System.out.println("Completed"));
  }

  private static class RandomStringSupplier implements Supplier<Stream<String>> {
    private int count;
    private String[] inputs;
    private Random r;
    private int bound;
    private boolean throwError;
    public RandomStringSupplier(String[] data, int length) {
      this.inputs = data;
      this.r = new Random();
      this.bound = length;
      this.count = 0;
      this.throwError = false;
    }

    @Override
    public Stream<String> get() {
      logger.info("Producing data");
      ++count;
      if (count == 4 && throwError) {
        throw new RuntimeException("Found error");
      }
      return Stream.of(this.inputs[this.r.nextInt(bound)]);
    }
  }
}
