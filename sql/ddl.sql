-- 创建库
create database if not exists zhixiang;

-- 切换库
use zhixiang;

-- 用户表
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    accessKey    varchar(256)                           null,
    secretKey    varchar(256)                           null,
    remainPoint  int          default 0                 null comment '剩余点数',
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户';




-- auto-generated definition
create table interface_info
(
    id               bigint auto_increment comment '主键'
        primary key,
    apiName          varchar(256)                           null comment '接口名',
    apiDescription   varchar(256)                           null comment '接口描述',
    apiUrl           varchar(512)                           null comment '接口地址',
    requestHeader    text                                   null comment '请求头',
    responseHeader   text                                   null comment '响应头',
    status           int          default 0                 not null comment '接口状态 0-默认',
    method           varchar(256)                           null comment '请求类型',
    userId           bigint                                 null comment '创建人',
    createTime       datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime       datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted        tinyint      default 0                 not null comment '是否删除(0-未删, 1-已删)',
    requestParams    text                                   null comment '请求参数',
    totalInvokeCount int          default 0                 null comment '接口调用次数',
    requestExample   text                                   null comment '示例请求',
    reducePoint      int          default 0                 null comment '调用接口扣除积分',
    returnFormat     varchar(256) default 'JSON'            null comment '返回格式',
    avatarUrl        varchar(1024)                          null comment '头像地址'
)
    comment '接口信息表';



create table user_interface_info
(
    id              bigint auto_increment comment 'id'
        primary key,
    userId          bigint                             null comment '接口调用者id',
    interfaceInfoId bigint                             null comment '接口id',
    totalNum        int      default 0                 not null comment '调用总次数',
    status          int      default 0                 not null comment '0-正常 1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
)
    comment '用户接口调用关系表';
