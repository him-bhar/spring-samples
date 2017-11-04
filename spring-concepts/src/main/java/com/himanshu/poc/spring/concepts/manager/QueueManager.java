package com.himanshu.poc.spring.concepts.manager;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

public class QueueManager {
  private final Queue queue;

  public QueueManager(MetricRegistry metrics, String name) {
    this.queue = new LinkedBlockingQueue<>();
    metrics.register(MetricRegistry.name(QueueManager.class, name, "size"), new Gauge<Integer>() {
      @Override
      public Integer getValue() {
        return queue.size();
      }
    });
  }
}