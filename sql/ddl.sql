-- 创建库
create database if not exists zhixiang;

-- 切换库
use zhixiang;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';


-- 接口信息表
create table if not exists zhixiang.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `apiName` varchar(256) null comment '接口名',
    `apiDescription` varchar(256) null comment '接口描述',
    `apiUrl` varchar(512) null comment '接口地址',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态 0-默认',
    `method` varchar(256) null comment '请求类型',
    `userId` bigint null comment '创建人',
    `createTime` datetime default 'CURRENT_TIMESTAMP' not null comment '创建时间',
    `updateTime` datetime default 'CURRENT_TIMESTAMP' not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息表';

insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('汪哲瀚', '6vR', 'www.shavon-oconner.io');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('丁立果', 'I2oY', 'www.bryan-casper.com');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('范绍齐', 'ulOv', 'www.moshe-schuster.biz');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('钱天宇', 'xdT', 'www.wilburn-franecki.info');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('范立果', '7Vfbk', 'www.gavin-blick.com');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('侯俊驰', 'I7KH', 'www.laurence-senger.co');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('傅晓博', 'co1R', 'www.devin-turner.org');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('夏君浩', 'SI7eV', 'www.viviana-bode.name');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('韦煜祺', '5mu', 'www.devona-harvey.name');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('熊炫明', '5RP', 'www.damon-dach.co');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('黎修洁', '8lW', 'www.chris-hermiston.name');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('严浩宇', '1A5', 'www.houston-gislason.net');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('夏嘉熙', 'BxpU', 'www.guadalupe-bartell.org');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('邵擎宇', 'XvsZ3', 'www.alton-sipes.com');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('毛思远', 'esu', 'www.bennie-wintheiser.org');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('方鸿涛', 'Mw7HJ', 'www.ian-conn.info');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('万明哲', '01HI', 'www.clint-reichert.com');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('邵航', 'O3', 'www.mike-bode.io');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('赵修杰', '0xHg', 'www.zula-bernier.net');
insert into zhixiang.`interface_info` (`apiName`, `apiDescription`, `apiUrl`) values ('洪思源', 'E44R', 'www.dannie-flatley.com');
