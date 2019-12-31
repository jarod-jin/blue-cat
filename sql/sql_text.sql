create database resource default character set utf8mb4 collate utf8mb4_general_ci;

grant all privileges on resource.* to resource@'%' identified by 'resource#2019';

flush privileges;