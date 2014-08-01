/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.24a-community-nt-log : Database - visa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `area` */

DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
  `areaId` int(10) NOT NULL auto_increment COMMENT '领区ID',
  `areaName` varchar(255) default NULL COMMENT '领区名称',
  `postDt` datetime default NULL COMMENT '发布时间',
  PRIMARY KEY  (`areaId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `area` */

insert  into `area`(`areaId`,`areaName`,`postDt`) values (1,'北京','2013-07-25 18:44:38');

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `countryId` int(10) NOT NULL auto_increment COMMENT '国家ID',
  `countryName` varchar(255) default NULL COMMENT '国家名称',
  `continentId` int(11) default NULL COMMENT '所属大洲',
  `postDt` datetime default NULL COMMENT '发布时间',
  PRIMARY KEY  (`countryId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `country` */

insert  into `country`(`countryId`,`countryName`,`continentId`,`postDt`) values (3,'澳大利亚',2,'2013-07-25 19:19:56');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customerId` int(10) NOT NULL auto_increment COMMENT '主键',
  `customerName` varchar(20) default NULL COMMENT '客户姓名',
  `company` varchar(100) default NULL COMMENT '公司',
  `telephone` varchar(20) default NULL COMMENT '电话',
  `address` varchar(200) default NULL COMMENT '地址',
  `salesmanId` varchar(20) default NULL COMMENT '所属销售id',
  `des` varchar(1000) default NULL COMMENT '备注',
  `postDt` datetime default NULL COMMENT '发布时间',
  PRIMARY KEY  (`customerId`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (1,'速度提升适当浮动幅度的对方的浮动幅度达到','嗖嗖嗖ddd','23434334ddd','速度等等等等等等ddd','tttt','似懂非懂辅导费dfdfddd','2013-07-09 14:39:35');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (2,'sdf','sdf','sdf','sdf','tttt','sdf','2013-07-09 14:54:06');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (5,'ggggg','ggggg','gggggg','ggggggggg','test','gggggggggggggg','2013-07-09 14:54:56');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (4,'uuuuuuuu','uuuu','uuuuuuu','uuuuuu','tttt','uuuuuuuuuuuuuuu','2013-07-09 14:54:25');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (6,'ete','et','etet','et','zzp','sfsdfdsf','2013-07-11 15:27:48');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (7,'aaaaaaaa','aaaaaaa','aaa','aaaaa','zzp','aaaaaaaaaaaa','2013-07-11 15:36:03');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (8,'gg','gg','gg','gg','zzp','gg','2013-07-11 16:13:19');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (9,'ggggggg','gggg','ggg','gggggggggggg','zzp','gggggggggggggg','2013-07-11 16:13:28');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (10,'你们是谁','你们是谁','你们是谁','你们是谁','zzp','你们是谁你们是谁','2013-07-11 16:13:56');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (11,'你们是','你们是谁','你们是谁','你们是谁','zzp','你们是谁','2013-07-11 16:14:07');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (12,'你们','你们是谁','你们是谁','你们是谁','zzp','你们是谁','2013-07-11 16:14:16');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (14,'点点滴滴','打发打发打发','大幅度','地方','zzp','','2013-07-12 11:16:32');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (15,'淡淡的','等待','等待','等待','rrr','淡淡的','2013-07-12 17:47:27');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (16,'111','2222','15810361284','sdfdsf','zzp','','2013-07-19 10:11:40');
insert  into `customer`(`customerId`,`customerName`,`company`,`telephone`,`address`,`salesmanId`,`des`,`postDt`) values (17,'123','13213','15810361294','123','zzp','','2013-07-19 10:51:29');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `orderId` int(10) NOT NULL auto_increment COMMENT '主键',
  `orderDate` date default NULL COMMENT '下单日期',
  `customerId` int(10) default NULL COMMENT '客户id',
  `productId` int(10) default NULL COMMENT '产品id',
  `nameList` varchar(200) default NULL COMMENT '客人名单',
  `nameListSize` int(8) default NULL COMMENT '客人数量',
  `status` int(1) default NULL COMMENT '目前状态',
  `salesmanId` varchar(20) default NULL COMMENT '销售id',
  `operatorId` varchar(20) default NULL COMMENT '操作员id',
  `operatorDes` varchar(100) default NULL COMMENT '订单状态（操作员录入）',
  `signDate` date default NULL COMMENT '送签日期',
  `signOperatorName` varchar(20) default NULL COMMENT '送签员姓名',
  `priceYsdj` decimal(10,2) default NULL COMMENT '应收单价',
  `priceQtys` decimal(10,2) default NULL COMMENT '其它应收款',
  `priceQtzc` decimal(10,2) default NULL COMMENT '其它支出款',
  `priceZjys` decimal(10,2) default NULL COMMENT '总计应收',
  `priceYfhk` decimal(10,2) default NULL COMMENT '已付货款',
  `priceZjyf` decimal(10,2) default NULL COMMENT '总计应付',
  `grossProfit` decimal(10,2) default NULL COMMENT '毛利润',
  `priceStatus` varchar(100) default NULL COMMENT '付款状态',
  `des` varchar(1000) default NULL COMMENT '备注',
  `ptTime` datetime default NULL,
  PRIMARY KEY  (`orderId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`orderId`,`orderDate`,`customerId`,`productId`,`nameList`,`nameListSize`,`status`,`salesmanId`,`operatorId`,`operatorDes`,`signDate`,`signOperatorName`,`priceYsdj`,`priceQtys`,`priceQtzc`,`priceZjys`,`priceYfhk`,`priceZjyf`,`grossProfit`,`priceStatus`,`des`,`ptTime`) values (1,'2013-07-12',15,2,'11',1,0,'rrr',NULL,NULL,NULL,NULL,'11.00','11.00','11.00','22.00','21.00','34.00','11.00','3','','2013-07-12 17:57:20');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `productId` int(10) NOT NULL auto_increment COMMENT '主键',
  `productName` varchar(100) default NULL COMMENT '产品名称',
  `countryName` varchar(20) default NULL COMMENT '国家名称',
  `productType` varchar(100) default NULL COMMENT '产品类型',
  `districtPlace` varchar(100) default NULL COMMENT '送签区域',
  `price` decimal(10,2) default NULL COMMENT '成本价格',
  `des` varchar(1000) default NULL COMMENT '备注',
  `postDt` datetime default NULL COMMENT '发布时间',
  PRIMARY KEY  (`productId`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`productId`,`productName`,`countryName`,`productType`,`districtPlace`,`price`,`des`,`postDt`) values (1,'testdddddddd','est','set','set','99999999.99','sdfdsfdsfdddddddddddddddd','2013-07-09 11:13:30');
insert  into `product`(`productId`,`productName`,`countryName`,`productType`,`districtPlace`,`price`,`des`,`postDt`) values (2,'出国旅游','出国旅游','出国旅游','出国旅游','23.00','出国旅游出国旅游出国旅游出国旅游出国旅游','2013-07-09 11:15:34');
insert  into `product`(`productId`,`productName`,`countryName`,`productType`,`districtPlace`,`price`,`des`,`postDt`) values (3,'出国旅游23','出国旅游23','出国旅游23','出国旅游23','12.00','出国旅游2出国333旅游2出国旅游2出国旅游2','2013-07-09 11:20:12');
insert  into `product`(`productId`,`productName`,`countryName`,`productType`,`districtPlace`,`price`,`des`,`postDt`) values (6,'等待','单独辅导','dfdf','dfdf','12.30','','2013-07-12 11:20:18');
insert  into `product`(`productId`,`productName`,`countryName`,`productType`,`districtPlace`,`price`,`des`,`postDt`) values (7,'111111111111111111','11111111111111111111','111111111111111111111111','11111111111111111111111111111','999999.90','','2013-07-19 14:14:45');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` varchar(20) NOT NULL default '' COMMENT '主键',
  `userName` varchar(20) default NULL COMMENT '用户名字',
  `roleId` int(10) default NULL COMMENT '角色id',
  `pwd` varchar(20) default NULL COMMENT '登陆密码',
  `managerId` varchar(20) default NULL COMMENT '经理id',
  `postDt` datetime default NULL COMMENT '发布日期',
  PRIMARY KEY  (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('user','user',1,'1','','2013-07-06 10:50:23');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('zzp','斯蒂芬',2,'zzp','test','2013-07-07 10:50:23');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('ritcher','ritcher',3,'ritcher','','2013-07-08 10:50:23');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('test','test',3,'123456','','2013-07-08 17:08:23');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('rrr','rrrtttt',2,'123456','ritcher','2013-07-08 17:13:43');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('tttt','tttt',2,'123456','','2013-07-08 17:14:02');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('yyy','yyyydddddd',2,'123456','ritcher','2013-07-08 17:14:44');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('uuu','uuuyyyyyy',5,'123456','','2013-07-08 17:14:54');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('sdfsdf','是大幅度三分得手',1,'123456','','2013-07-10 14:27:07');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('eeee','厄尔二二',1,'123456','','2013-07-10 14:38:18');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('sdfds','是大幅度三分得手房贷首付',1,'123456','','2013-07-10 14:41:24');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('的','ddfdf',1,'123456','','2013-07-12 11:23:13');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('q','q',4,'123456','','2013-07-12 17:58:20');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('111','1111',2,'123456','','2013-07-19 11:00:31');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('123','123',3,'123456','','2013-07-19 11:12:52');
insert  into `user`(`userId`,`userName`,`roleId`,`pwd`,`managerId`,`postDt`) values ('1232','1232',2,'123456','ritcher','2013-07-19 11:14:42');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
