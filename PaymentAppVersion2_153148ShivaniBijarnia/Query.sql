create database paymentapp;
use paymentapp;
drop table customer;
create table customer(name varchar(20), mobile varchar(10) primary key, balance decimal(30, 0));