package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

public class PostLombokTest {
  @Test
  public void testGetters() {
    Post p = new Post(1l, "title", "sample-content");
    Assert.assertTrue(p.getId() == 1l);
    Assert.assertTrue(p.getTitle().equalsIgnoreCase("title"));
    Assert.assertTrue(p.getContent().equalsIgnoreCase("sample-content"));
  }
}
