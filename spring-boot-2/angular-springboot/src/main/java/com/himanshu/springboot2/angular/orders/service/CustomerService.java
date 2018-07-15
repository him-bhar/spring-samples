package com.himanshu.springboot2.angular.orders.service;

import com.himanshu.springboot2.angular.orders.dao.CustomerDao;
import com.himanshu.springboot2.angular.orders.dao.ICustomerDaoNonData;
import com.himanshu.springboot2.angular.orders.dao.ItemDao;
import com.himanshu.springboot2.angular.orders.dao.OrderDao;
import com.himanshu.springboot2.angular.orders.entity.Customer;
import com.himanshu.springboot2.angular.orders.entity.Item;
import com.himanshu.springboot2.angular.orders.entity.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
  private final CustomerDao customerDao;
  private final ICustomerDaoNonData customerDaoNonData;
  private final ItemDao itemDao;
  private final OrderDao orderDao;

  public CustomerService(CustomerDao customerDao, ICustomerDaoNonData customerDaoNonData, ItemDao itemDao,
                         OrderDao orderDao) {
    this.customerDao = customerDao;
    this.customerDaoNonData = customerDaoNonData;
    this.itemDao = itemDao;
    this.orderDao = orderDao;
  }

  public Iterable<Customer> listAll() {
    return this.customerDao.findAll();
  }

  @Transactional(transactionManager="sampleDSTransactionManager", isolation = Isolation.SERIALIZABLE)
  public Customer getAndUpdateCustomerCountry(long id, String country) {
    Customer c = this.customerDaoNonData.loadCustomerById(id);
    c.setCountry(country);
    customerDaoNonData.updateCustomer(c);
    Customer customer = customerDaoNonData.loadCustomerById(id);
    return customer;
  }

  @Transactional(transactionManager="sampleDSTransactionManager")
  public List<Item> getItems() {
    List<Item> items = new ArrayList<>();
    itemDao.findAll().forEach(items::add);
    return items;
  }

  @Transactional(transactionManager="sampleDSTransactionManager")
  public List<Order> getOrdersViaJoinFetch() {
    List<Order> orders = new ArrayList<>();
    orderDao.listAllViaJoinFetch().forEach(orders::add);
    return orders;
  }

  @Transactional(transactionManager="sampleDSTransactionManager")
  public List<Order> getOrders() {
    List<Order> orders = new ArrayList<>();
    orderDao.findAll().forEach(orders::add);
    orders.stream().forEach(order -> order.getCustomer());
    return orders;
  }

}
