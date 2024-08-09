package com.app.service;

import com.app.DAO.AgencyDao;
import com.app.DAO.ConsumerDao;
import com.app.DAO.OrderDao;
import com.app.DAO.PaymentDao;
import com.app.DTO.*;


import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.entities.Agency;
import com.app.entities.Consumer.Consumer;
import com.app.entities.Consumer.ConsumerType;

import com.app.entities.Cylinder.Cylinder;
import com.app.entities.Order.Order;
import com.app.entities.Order.OrderStatus;
import com.app.entities.Payment.Payment;
import com.app.entities.Payment.PaymentType;
import com.app.entities.Policy.CancellationPolicy;
import com.app.entities.Registeration.Registration;
import com.app.entities.Registeration.RegistrationStatus;

import com.app.entities.Review;
import lombok.extern.slf4j.XSlf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    ConsumerDao cDao;

    @Autowired
    AgencyDao aDao;

    @Autowired
    PaymentDao pDao;


    @Autowired
    OrderDao oDao;

    @Autowired
    ModelMapper mapper;


    /******************Consumer***********************/
    //For Login
    public Consumer findByEmailAndPassword(String email,String password){
        return cDao.findByEmailAndPassword(email,password);
    }


    //Required during registeration
    public Consumer findByEmail(String email){
        return cDao.findByEmail(email).orElse(null);
    }


    public Consumer findById(Long id){
        return cDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No consumer found"));
    }



    //Delete Consumer

    public void deleteUserById(Long id){

        Consumer c=cDao.findById(id).orElseThrow(()->new ResourceNotFoundException("Consumer not found"));
        Agency a=c.getAgency();
        //One To Many relationship set fetch type to EAGER
        List<Consumer> consumerList=a.getConsumers();
        int i=consumerList.indexOf(c);
        consumerList.remove(i);
        a.setConsumers(consumerList);
        aDao.save(a);
        cDao.delete(c);
    }

    //SignUp
    public Consumer userSignup(SignupDTO signupDto){

        Consumer c=findByEmail(signupDto.getEmail());
        if(c!=null){
            Consumer c1=mapper.map(signupDto,Consumer.class);
            return cDao.save(c1);
        }else{
            throw new ApiException("Consumer already exists");
        }
    }


    //Registeration
    public Consumer userRegisteration(UserDetailsDTO uDto){
        Consumer c=cDao.findById(uDto.getConsumerId()).orElseThrow(()->new ResourceNotFoundException("User not found"));

        Period p=Period.between(LocalDate.parse(uDto.getDoB()),LocalDate.now());


        if(p.getYears()>=18){
            c.setDoB(LocalDate.parse(uDto.getDoB()));

        }else{
            throw new RuntimeException("Must be atleast 18 years old");
        }
        c.setConsumerType(ConsumerType.valueOf(uDto.getConsumerType().toString()));
        c.setAddress(uDto.getAddress());
        c.setPhoneNo(uDto.getPhoneNo());


        //After registering set the registeration status to pending
        Registration r=new Registration();
        r.setStatus(RegistrationStatus.PENDING);
        c.setRegisteration(r);
        return cDao.save(c);
    }


    //Applying for new gas connection
    public void applyForNewConnection(Long uId){
        Consumer c=cDao.findById(uId).orElseThrow(()->new ResourceNotFoundException("No user found"));
        String consumerType=c.getConsumerType().toString();
        int count=c.getConnectionCount();
        if(consumerType.equals("COMMERCIAL")){
            count++;
        }

        if(consumerType.equals("RESIDENTIAL")){
            if(count<2){
                count++;
            }else{
                throw new RuntimeException("Already reached maximum connection count for residential connection");
            }
        }
    }


    //Update User details
    public Consumer updateUserDetails(UserDetailsDTO uDto){
        Consumer c=cDao.findById(uDto.getConsumerId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        c.setConsumerType(ConsumerType.valueOf(uDto.getConsumerType().toString()));
        c.setAddress(uDto.getAddress());
        c.setPhoneNo(uDto.getPhoneNo());
        c.setEmail(uDto.getEmail());
        c.setPassword(uDto.getPassword());
        return cDao.save(c);
    }

    /**************************PAYMENT**********************************/
    //Adding Payment Methods
    public Payment addPaymentMethod(AddPaymentDto pDto){


        Consumer c=cDao.getConsumerWithPayment(pDto.getUId()).orElseThrow(()->new ResourceNotFoundException("No user found"));
        List<Payment> pList= c.getPaymentMethods();
        Payment p=new Payment();
        p.setConsumer(c);
        p.setType(PaymentType.valueOf(pDto.getPaymentType().toString()));
        pList.add(p);
        c.setPaymentMethods(pList);
        cDao.save(c);
        return p;
    }


    //Deleteing payment methods
    public void deletePaymentMethod(Long pId){
        Payment p=pDao.findById(pId).orElseThrow(()->new ResourceNotFoundException("No payment found"));
        Consumer c=p.getConsumer();
        Consumer c1=cDao.getConsumerWithPayment(c.getConsumerId()).orElseThrow(()->new ResourceNotFoundException("No payment found"));
        List<Payment> l1=c1.getPaymentMethods();
        int i=l1.indexOf(p);
        l1.remove(i);
        c1.setPaymentMethods(l1);
        cDao.save(c1);
    }



    /***************************ORDER METHODS******************************************************/


    //1.Place a Order

    public void placeOrder(OrderReqDto reqDto){
        Consumer c=cDao.getConsumerWithOrders(reqDto.getUserId()).orElseThrow(()->new ResourceNotFoundException(""));
        List<Order> orders=c.getOrders();
        Order o=new Order();
        o.setAgency(aDao.findById(reqDto.getAgencyId()).orElseThrow(()->new ResourceNotFoundException("Agency not found")));
        o.setConsumer(c);
        o.setPayment(pDao.findById(reqDto.getPaymentId()).orElseThrow(()->new ResourceNotFoundException("payment not found")));
        List<Cylinder> cList=new ArrayList<>();
        for(int i=0;i<reqDto.getCylinderDTOList().size();i++){
           Cylinder cylinder= mapper.map(reqDto.getCylinderDTOList().get(i),Cylinder.class);
           cList.add(cylinder);
        }
        o.setCylinders(cList);

        Double total=0d;
        for(int i=0;i<cList.size();i++){
            total=total+cList.get(i).getPrice();
        }

        o.setOrderTotal(total);


        o.setOrderInstruction(reqDto.getOrderInstruction());
        o.setOrderDate(LocalDate.now());
        o.setExpectedDeliveryDate(LocalDate.now().plusDays(10));


        //By default the status will be pending
        o.setOrderStatus(OrderStatus.PENDING);

        o.setCancellationPolicy(CancellationPolicyService.getCancellationPolicy());


        orders.add(o);
        c.setOrders(orders);
        cDao.save(c);
    }

    public void confirmOrder(Long oId){
        Order o=oDao.findById(oId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(OrderStatus.CONFIRMED);
        oDao.save(o);
    }

    public List<Order> findAllPendingOrders(){
        List<Consumer> consumerList=cDao.getAllConsumerWithOrders();
        List<Order> pendingOrders=new ArrayList<>();
        consumerList.forEach(consumer -> {
            List<Order> orders=consumer.getOrders();
            orders.forEach(order -> {
                if(order.getOrderStatus().toString().equals("PENDING")){
                    pendingOrders.add(order);
                }
            });
        });
        return pendingOrders;
    }


    public List<Order> getAllOrders(){
        List<Order> allOrdersList=new ArrayList<>();
        List<Consumer> consumerList=cDao.getAllConsumerWithOrders();
        consumerList.forEach(c->{
            List<Order> orders=c.getOrders();
            orders.forEach(o-> allOrdersList.add(o));
        });
        return  allOrdersList;
    }


    public List<Order> getOrdersByConsumerId(Long id){
        List<Order> allOrdersList=new ArrayList<>();
        List<Consumer> consumerList=cDao.getAllConsumerWithOrders();
        consumerList.forEach(c->{
            if(c.getConsumerId()==id){
                List<Order> orders=c.getOrders();
                orders.forEach(o->allOrdersList.add(o));
            }
        });
        return  allOrdersList;
    }


    public void cancelOrderByAdmin(Long oId){
        Order o=oDao.findById(oId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(OrderStatus.CANCELLED);

        //100% Refund
        o.setRefundedAmt(o.getOrderTotal());
        oDao.save(o);
    }
    public void cancelOrderByConsumer(Long oId){
        Order o=oDao.findById(oId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        if(o.getOrderStatus().toString().equals("DELIVERED")){
            throw new ApiException("Order is already delivered");
        }
        CancellationPolicy cp =o.getCancellationPolicy();
        Integer days=cp.getDays();
        Integer percentage=cp.getRefundDeductionPercentage();

        //Refund based on refund policy
        Period p=Period.between(LocalDate.now(),o.getOrderDate());

        if(p.getDays()>days){
            throw new ApiException("Violating cancellation policy");
        }else{
            o.setOrderStatus(OrderStatus.REFUNDED);
            o.setRefundedAmt(o.getRefundedAmt()*(percentage/100));
        }
        oDao.save(o);
    }

    //Pending
    //Assigning DeliveryGuy to Order
    //changing order status and orderReply




    /*******************REVIEW*********************************************/

    public void postOrUpdateReview(ReviewReqDTO reqDto){
        Order o=oDao.findById(reqDto.getOrderId()).orElseThrow(()->new ResourceNotFoundException("No order found"));
        Review r=mapper.map(reqDto, Review.class);
        o.setReview(r);
        oDao.save(o);
    }

    public void deleteReview(Long oId){
        Order o=oDao.findById(oId).orElseThrow(()->new ResourceNotFoundException("No order found"));
        o.setReview(null);
    }


}
