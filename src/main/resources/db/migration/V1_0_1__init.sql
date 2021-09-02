-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `title` varchar(100) NOT NULL COMMENT '标题',
                           `summary` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '摘要',
                           `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
                           `coverPic` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '文章封面图片',
                           `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                           `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `status` enum('PUBLISHED','DRAFT','DELETED') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PUBLISHED' COMMENT '文章状态',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'这是一个草率的开始','项目的初始化与运行','# 这是一个草率的开始\n\n## 简介\n\n一个主要靠拼凑而成的个人博客项目，共分为了 [前台](https://github.com/WindSnowLi/vue-ssr-blog) 、 [后台](https://github.com/WindSnowLi/vue-admin-blog) 、 [api](https://github.com/WindSnowLi/w-blog-api) 三个部分。\n\n1. [api](https://github.com/WindSnowLi/w-blog-api)\n后端基于 `SpringBoot` 。主要依赖 `mybatis` 、 `fastjson` 、 `DruidDataSource` 、 `Lombok` 、 `java-jwt` 、 `aliyun-sdk-oss` 、 `knife4j` 等，数据库使用的是 `MySQL8.0+`\n\n2. [前台](https://github.com/WindSnowLi/vue-ssr-blog)\n前台的主要样式是来源于网络上了一个 `BizBlog` 模板，最初来源于哪我不得而知，在原本的基础上改写成了 `nuxtJs` 项目。\n3. [后台](https://github.com/WindSnowLi/vue-admin-blog)\n后台UI套用的[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，基本是直接拿来用了，想自己定制着实实力不允许。\n4. [示例](https://www.blog.hiyj.cn/)：[绿色食品——菜狗](https://www.blog.hiyj.cn/)\n\n## 本地启动\n\n### [api](https://github.com/WindSnowLi/w-blog-api)：前台后台请求的api使用的是同一个项目\n\n1. `git clone https://github.com/WindSnowLi/w-blog-api.git` 克隆项目到本地\n2. `mvn clean install dependency:tree` 安装依赖\n3. 修改开发环境 `application-dev.yml` 和生产环境 `application-prod.yml` 中的数据库配置信息； `knife4j` 只在开发环境中激活。\n4. 创建数据库配置中指定名称的空数据库，`UTF8`编码\n5. `mvn clean package -Dmaven.test.skip=true` 跳过测试并生成 `jar` 包\n6. `java -jar 生成的包名.jar` 运行开发配置环境，初次运行会自动初始化数据库\n7. 访问 `http://127.0.0.1:8888/doc.html` 查看 `api` 文档\n8. *推荐使用IDEA打开项目文件夹自动处理依赖、方便运行*\n\n### [前台](https://github.com/WindSnowLi/vue-ssr-blog)\n\n1. `git clone https://github.com/WindSnowLi/vue-ssr-blog.git` 克隆项目到本地\n2. `npm install` 安装依赖\n3. 可修改 `config/sitemap.xml` 文件中的 `host` 地址，用于生成访问地图\n4. 可修改 `nuxt.config.js` 中的端口号\n5. 可修改 `package.json` 文件中的 `script` 中的 `BASE_URL` 来指定后端 `api` 地址\n6. `npm run build` 编译\n7. `npm start` 本地运行\n\n### [后台](https://github.com/WindSnowLi/vue-admin-blog)\n\n1. `git clone https://github.com/WindSnowLi/vue-admin-blog.git` 克隆项目到本地\n2. `npm install` 安装依赖\n3. `npm run dev` 使用模拟数据预览界面\n4. 修改 `.env.production` 文件中的 `VUE_APP_BASE_API` 地址为后端 `api` 的地址\n5. `npm run build:prod` 编译\n6. `dist` 文件夹下的为编译好的文件，可放到 `http` 服务器下（可以使用 `npm` 安装 `http-server` ）进行访问\n\n## 界面展示\n\n### 前台\n\n![首页](https://pic.hiyj.cn/images/2021/08/30/8be350dc4ad9f76c10f7daa8f0ec2f83.png)\n\n<br>\n\n![文章详情](https://pic.hiyj.cn/images/2021/08/30/d921a4e9d688c8e42e4cb491e81ea29f.png)\n\n<br>\n\n![友链](https://pic.hiyj.cn/images/2021/08/30/1e6a95dba9dffe2c518fb1114d27f9ef.png)\n\n<br>\n\n![关于信息](https://pic.hiyj.cn/images/2021/08/30/2a71cb94b94aed68d5628ee41beb0359.png)\n\n### 后台\n\n![首页](https://pic.hiyj.cn/images/2021/08/30/c058c6879cad0dc8db994a7dc57f1de6.png)\n\n<br>\n\n![创建文章](https://pic.hiyj.cn/images/2021/08/30/5a9b1e429a934801704cc9ef9526ff60.png)\n\n<br>\n\n![管理文章](https://pic.hiyj.cn/images/2021/08/30/b9a4cc395e4e02ff996c198f39c10895.png)\n\n![文章列表](https://pic.hiyj.cn/images/2021/08/30/7e69e00b415213d37034dd49d236d18e.png)\n\n<br>\n\n![友链管理](https://pic.hiyj.cn/images/2021/08/30/2cd2f03ab3eab5ea0e14a7ced2695d09.png)\n\n<br>\n\n![关于信息](https://pic.hiyj.cn/images/2021/08/30/5582f506f1a93b114baef9d9977841ea.png)\n','','2021-08-31 12:01:53','2021-08-31 20:37:16','PUBLISHED');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_label`
--

DROP TABLE IF EXISTS `article_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_label` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `name` varchar(100) NOT NULL COMMENT 'lable名称',
                                 `coverPic` varchar(510) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'https://www.pic.firstmeet.xyz/images/2021/05/26/d593adeb90a35948f7f7680a8bf7416b.md.jpg' COMMENT '封面',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `article_label_UN` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_label`
--

LOCK TABLES `article_label` WRITE;
/*!40000 ALTER TABLE `article_label` DISABLE KEYS */;
INSERT INTO `article_label` VALUES (1,'default','https://pic.hiyj.cn/images/2021/05/26/d593adeb90a35948f7f7680a8bf7416b.md.jpg');
/*!40000 ALTER TABLE `article_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_map_label`
--

DROP TABLE IF EXISTS `article_map_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_map_label` (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `article_id` int NOT NULL COMMENT '文章id',
                                     `label_id` int NOT NULL COMMENT '标签ID',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `article_map_lable_un` (`article_id`,`label_id`),
                                     KEY `article_map_label_FK_1` (`label_id`),
                                     CONSTRAINT `article_map_label_FK` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT `article_map_label_FK_1` FOREIGN KEY (`label_id`) REFERENCES `article_label` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_map_label`
--

LOCK TABLES `article_map_label` WRITE;
/*!40000 ALTER TABLE `article_map_label` DISABLE KEYS */;
INSERT INTO `article_map_label` VALUES (1,1,1);
/*!40000 ALTER TABLE `article_map_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_map_type`
--

DROP TABLE IF EXISTS `article_map_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_map_type` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `article_id` int NOT NULL COMMENT '对应文章ID',
                                    `type_id` int NOT NULL COMMENT '所属分类',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `article_map_type_UN` (`article_id`,`type_id`),
                                    KEY `article_map_type_FK_1` (`type_id`),
                                    CONSTRAINT `article_map_type_FK` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CONSTRAINT `article_map_type_FK_1` FOREIGN KEY (`type_id`) REFERENCES `article_label` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_map_type`
--

LOCK TABLES `article_map_type` WRITE;
/*!40000 ALTER TABLE `article_map_type` DISABLE KEYS */;
INSERT INTO `article_map_type` VALUES (1,1,1);
/*!40000 ALTER TABLE `article_map_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '评论会话ID',
                           `target_id` int DEFAULT NULL COMMENT '评论所属内容ID',
                           `session_type` enum('ARTICLE','TAG','TYPE','ABOUT','MESSAGE') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论类型',
                           `from_user` int NOT NULL COMMENT '评论来源用户',
                           `parent_id` int DEFAULT NULL COMMENT '所属根会话ID',
                           `status` enum('PASS','VERIFY','DELETE') NOT NULL DEFAULT 'VERIFY' COMMENT '评论状态',
                           `content` longtext COMMENT '评论内容',
                           `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                           `to_user` int DEFAULT NULL COMMENT '回复给的用户ID',
                           PRIMARY KEY (`id`),
                           KEY `comment_FK` (`from_user`),
                           KEY `comment_FK_1` (`to_user`),
                           CONSTRAINT `comment_FK` FOREIGN KEY (`from_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `comment_FK_1` FOREIGN KEY (`to_user`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'ARTICLE',1,NULL,'PASS','评论测试','2021-08-31 17:07:56',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_links`
--

DROP TABLE IF EXISTS `friend_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_links` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接的值',
                                `title` varchar(255) DEFAULT NULL COMMENT '链接的主题',
                                `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                `email` varchar(100) NOT NULL COMMENT '链接申请的邮箱',
                                `status` enum('PASS','REFUSE','APPLY','HIDE','DELETE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'APPLY' COMMENT '申请状态',
                                `coverPic` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片url地址',
                                `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                                `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `links_UN` (`link`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_links`
--

LOCK TABLES `friend_links` WRITE;
/*!40000 ALTER TABLE `friend_links` DISABLE KEYS */;
INSERT INTO `friend_links` VALUES (1,'https://www.blog.hiyj.cn/','绿色小菜狗','芹菜+白菜+苟=绿色小菜狗','706623475@qq.com','PASS','https://pic.hiyj.cn/images/2021/07/04/7c99ac62e6de300a5256ec3be56cb2a1.md.gif','2021-08-08 22:35:51','2021-09-02 21:40:00');
/*!40000 ALTER TABLE `friend_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_user`
--

DROP TABLE IF EXISTS `other_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `other_user` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `other_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方身份标识符',
                              `other_platform` enum('GITEE','GITHUB') NOT NULL COMMENT '第三方平台名称',
                              `user_id` int NOT NULL COMMENT '对应本地的用户ID',
                              `access_token` varchar(255) DEFAULT NULL COMMENT '身份密钥信息',
                              `refresh_token` varchar(255) DEFAULT NULL COMMENT '过期后用于刷新密钥信息',
                              `scope` varchar(255) DEFAULT NULL COMMENT '授权信息',
                              `created_at` int DEFAULT NULL COMMENT '密钥创建时间',
                              `expires_in` int DEFAULT NULL COMMENT '密钥有效期',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `other_user_un` (`other_id`,`other_platform`),
                              KEY `other_user_FK` (`user_id`),
                              CONSTRAINT `other_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_user`
--

LOCK TABLES `other_user` WRITE;
/*!40000 ALTER TABLE `other_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
                              `name` varchar(100) NOT NULL COMMENT '权限名称',
                              `intro` varchar(255) DEFAULT NULL COMMENT '权限介绍',
                              `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `status` enum('NORMAL','IGNORE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NORMAL' COMMENT '权限状态',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `permission_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'COMMENT','评论权限','2021-08-26 11:49:30','2021-08-26 17:46:13','NORMAL'),(2,'BACKGROUND-LOGIN','后台登陆权限','2021-08-26 11:50:56','2021-08-26 17:46:13','NORMAL'),(3,'CREATE-ARTICLE','创建文章权限','2021-08-26 11:51:37','2021-08-26 17:46:13','NORMAL'),(4,'DELETE-ARTICLE','删除文章权限','2021-08-26 11:52:06','2021-08-26 17:46:13','NORMAL'),(5,'GET-ARTICLE','获取文章权限','2021-08-26 11:52:48','2021-08-26 17:46:13','NORMAL'),(6,'GET-COMMENT','获取评论权限','2021-08-26 11:53:26','2021-08-26 17:46:13','NORMAL'),(7,'VERIFY-COMMENT','审核评论权限','2021-08-26 11:54:57','2021-08-26 17:46:13','NORMAL'),(8,'VERIFY-LINK','审核链接权限','2021-08-26 11:55:52','2021-08-26 17:46:13','NORMAL'),(9,'UPLOAD-FILE','上传文件权限','2021-08-26 11:56:31','2021-08-26 17:46:13','NORMAL'),(10,'UI-SETTING','设置UI权限','2021-08-26 11:57:08','2021-08-26 17:46:13','NORMAL'),(11,'STORE-SETTING','设置存储配置文件权限','2021-08-26 11:57:54','2021-08-26 17:46:13','NORMAL'),(12,'SYS-SETTING','系统设置权限','2021-08-26 11:58:13','2021-08-26 17:46:13','NORMAL'),(13,'USER-BASE-INFO','用户基础信息设置权限','2021-08-26 11:58:40','2021-08-26 17:46:13','NORMAL'),(14,'ABOUT-INFO','关于信息设置权限','2021-08-26 11:59:01','2021-08-26 17:46:13','NORMAL'),(15,'UPDATE-ARTICLE','更新文章权限','2021-08-26 21:26:06','2021-08-26 21:26:06','NORMAL'),(16,'DELETE-FILE','删除云文件权限','2021-08-26 21:30:11','2021-08-26 21:30:11','NORMAL');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                        `name` varchar(100) NOT NULL COMMENT '角色名称',
                        `intro` varchar(255) DEFAULT NULL COMMENT '角色介绍',
                        `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `status` enum('NORMAL','IGNORE') NOT NULL DEFAULT 'NORMAL',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `role_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'admin','管理员角色','2021-08-26 11:47:15','2021-08-26 16:13:03','NORMAL'),(3,'user','普通用户角色','2021-08-26 11:47:44','2021-08-26 12:00:15','NORMAL'),(4,'visitor','访客角色','2021-08-26 12:00:15','2021-08-26 12:00:20','NORMAL');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_map_permission`
--

DROP TABLE IF EXISTS `role_map_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_map_permission` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `role_id` int NOT NULL COMMENT '角色ID',
                                       `permission_id` int DEFAULT NULL COMMENT '权限ID',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `role_map_permission_un` (`role_id`,`permission_id`),
                                       KEY `role_map_permission_FK_1` (`permission_id`),
                                       CONSTRAINT `role_map_permission_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                       CONSTRAINT `role_map_permission_FK_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_map_permission`
--

LOCK TABLES `role_map_permission` WRITE;
/*!40000 ALTER TABLE `role_map_permission` DISABLE KEYS */;
INSERT INTO `role_map_permission` VALUES (1,2,1),(2,2,2),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,2,7),(8,2,8),(9,2,9),(10,2,10),(11,2,11),(12,2,12),(13,2,13),(14,2,14),(20,2,15),(21,2,16),(15,3,1),(16,3,5),(17,3,6),(18,4,5),(19,4,6);
/*!40000 ALTER TABLE `role_map_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_setting`
--

DROP TABLE IF EXISTS `sys_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_setting` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `item` enum('filing_icp','filing_security','background_list','oss','admin_url','other_login','sundry') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项',
                               `value` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '值',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `sys_setting_UN` (`item`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COMMENT='系统设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_setting`
--

LOCK TABLES `sys_setting` WRITE;
/*!40000 ALTER TABLE `sys_setting` DISABLE KEYS */;
INSERT INTO `sys_setting` VALUES (4,'oss','{\"template\":{\"title\":\"系统存储配置\",\"type\":\"object\",\"required\":[\"endpoint\",\"accessKeyId\",\"accessKeySecret\",\"bucketName\",\"rootPath\",\"articleCoverImagePath\",\"articleImagePath\",\"avatarImagePath\",\"callbackUrl\"],\"properties\":{\"endpoint\":{\"type\":\"string\",\"title\":\"endpoint\",\"description\":\"请输入OSS节点描述\",\"ui:options\":{\"width\":\"30%\"}},\"accessKeyId\":{\"title\":\"accessKeyId\",\"type\":\"string\",\"description\":\"请OSS用户ID\",\"ui:options\":{\"width\":\"30%\"}},\"accessKeySecret\":{\"title\":\"accessKeySecret\",\"type\":\"string\",\"description\":\"请输入OSS密钥\",\"ui:options\":{\"width\":\"30%\"}},\"bucketName\":{\"title\":\"bucketName\",\"type\":\"string\",\"description\":\"请输入bucket名字\",\"ui:options\":{\"width\":\"30%\"}},\"rootPath\":{\"title\":\"项目存储根路径\",\"type\":\"string\",\"description\":\"请输入项目存储根路径\",\"default\":\"Blog/\",\"ui:options\":{\"width\":\"30%\"}},\"articleCoverImagePath\":{\"title\":\"文章封面目录\",\"type\":\"string\",\"description\":\"请输入文章封面目录\",\"default\":\"Blog/image/articleCoverImage\",\"ui:options\":{\"width\":\"30%\"}},\"articleImagePath\":{\"title\":\"内容图片路径\",\"type\":\"string\",\"description\":\"请输入文章内容图片路径\",\"default\":\"Blog/image/articleImage\",\"ui:options\":{\"width\":\"30%\"}},\"avatarImagePath\":{\"title\":\"用户头像路径\",\"type\":\"string\",\"description\":\"请输入用户头像路径\",\"default\":\"Blog/image/avatar\",\"ui:options\":{\"width\":\"30%\"}},\"callbackUrl\":{\"title\":\"OSS上传回调url\",\"type\":\"string\",\"description\":\"请输入OSS上传回调路径\",\"default\":\"***.com/api/file/callback\",\"ui:options\":{\"width\":\"30%\"}},\"status\":{\"title\":\"是否开启文件上传\",\"type\":\"boolean\",\"default\":false,\"ui:options\":{\"width\":\"30%\"}}}},\"storage\":{\"accessKeyId\":\"***\",\"avatarImagePath\":\"Blog/image/avatar\",\"bucketName\":\"***\",\"endpoint\":\"***\",\"accessKeySecret\":\"***\",\"callbackUrl\":\"https://*****.com/api/file/callback\",\"rootPath\":\"Blog\",\"articleImagePath\":\"Blog/image/articleImage\",\"articleCoverImagePath\":\"Blog/image/articleCoverImage\",\"status\":false}}'),(14,'other_login','{\"gitee\":{\"template\":{\"title\":\"Gitee登录密钥\",\"type\":\"object\",\"required\":[\"clientId\",\"clientSecret\"],\"properties\":{\"clientId\":{\"description\":\"程序ID\",\"type\":\"string\",\"title\":\"Client Id\"},\"clientSecret\":{\"description\":\"程序密钥\",\"title\":\"Client Secret\",\"type\":\"string\"},\"status\":{\"default\":false,\"ui:options\":{\"width\":\"33.333%\"},\"title\":\"是否开启Gitee登录\",\"type\":\"boolean\"}}},\"client\":{\"clientId\":\"***\",\"clientSecret\":\"***\",\"status\":true}}}'),(15,'admin_url','http://127.0.0.1:8080/'),(16,'background_list','http://pic.hiyj.cn/images/2021/05/26/71139a2f9b4e9cd737148c1980e2ebb8.jpg\nhttp://pic.hiyj.cn/images/2021/05/26/d43c04246031dfb3b8de8ee5109a4576.jpg\nhttp://pic.hiyj.cn/images/2021/05/26/5605d088eba4887a8bea873bc80f035d.jpg\nhttp://pic.hiyj.cn/images/2021/05/26/88833c15c93325785a3a3d4186651755.jpg\nhttp://pic.hiyj.cn/images/2021/05/26/fd75e33356e87d5cfd5d5edf4ac4a1ec.jpg\nhttp://pic.hiyj.cn/images/2021/05/26/95c0d093c821626a9fbe6a4d5afff4cd.jpg'),(21,'sundry','{\"template\":{\"title\":\"杂项配置\",\"type\":\"object\",\"properties\":{\"articleComment\":{\"default\":false,\"ui:options\":{\"width\":\"33.333%\"},\"title\":\"文章是否开启评论\",\"type\":\"boolean\"},\"aboutComment\":{\"default\":false,\"ui:options\":{\"width\":\"33.333%\"},\"title\":\"关于信息是否开启评论\",\"type\":\"boolean\"}}},\"sundry\":{\"articleComment\":true,\"aboutComment\":true}}');
/*!40000 ALTER TABLE `sys_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ui_config`
--

DROP TABLE IF EXISTS `ui_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ui_config` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int NOT NULL COMMENT '所属用户ID',
                             `item` enum('main_title','topbar_title','footer','background_list','about','contact') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属配置项',
                             `value` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '该项值',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `ui_config_UN` (`user_id`,`item`),
                             CONSTRAINT `ui_config_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ui_config`
--

LOCK TABLES `ui_config` WRITE;
/*!40000 ALTER TABLE `ui_config` DISABLE KEYS */;
INSERT INTO `ui_config` VALUES (119,1,'background_list','https://pic.hiyj.cn/images/2021/05/26/40e469f75a6844bb570c6b8481224ae9.jpg\nhttps://pic.hiyj.cn/images/2021/05/26/75512149904ab29eec72d7010c1312e5.jpg\nhttps://pic.hiyj.cn/images/2021/05/26/88833c15c93325785a3a3d4186651755.jpg\nhttps://pic.hiyj.cn/images/2021/05/26/11e4dfc877647d8badeddee94515bd68.jpg'),(130,1,'about','# 这是一个草率的开始\n\n## 简介\n\n一个主要靠拼凑而成的个人博客项目，共分为了 [前台](https://github.com/WindSnowLi/vue-ssr-blog) 、 [后台](https://github.com/WindSnowLi/vue-admin-blog) 、 [api](https://github.com/WindSnowLi/w-blog-api) 三个部分。\n\n1. [api](https://github.com/WindSnowLi/w-blog-api)\n后端基于 `SpringBoot` 。主要依赖 `mybatis` 、 `fastjson` 、 `DruidDataSource` 、 `Lombok` 、 `java-jwt` 、 `aliyun-sdk-oss` 、 `knife4j` 等，数据库使用的是 `MySQL8.0+`\n\n2. [前台](https://github.com/WindSnowLi/vue-ssr-blog)\n前台的主要样式是来源于网络上了一个 `BizBlog` 模板，最初来源于哪我不得而知，在原本的基础上改写成了 `nuxtJs` 项目。\n3. [后台](https://github.com/WindSnowLi/vue-admin-blog)\n后台UI套用的[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，基本是直接拿来用了，想自己定制着实实力不允许。\n4. [示例](https://www.blog.hiyj.cn/)：[绿色食品——菜狗](https://www.blog.hiyj.cn/)\n\n## 本地启动\n\n### [api](https://github.com/WindSnowLi/w-blog-api)：前台后台请求的api使用的是同一个项目\n\n1. `git clone https://github.com/WindSnowLi/w-blog-api.git` 克隆项目到本地\n2. `mvn clean install dependency:tree` 安装依赖\n3. 修改开发环境 `application-dev.yml` 和生产环境 `application-prod.yml` 中的数据库配置信息； `knife4j` 只在开发环境中激活。\n4. 创建数据库配置中指定名称的空数据库，`UTF8`编码\n5. `mvn clean package -Dmaven.test.skip=true` 跳过测试并生成 `jar` 包\n6. `java -jar 生成的包名.jar` 运行开发配置环境，初次运行会自动初始化数据库\n7. 访问 `http://127.0.0.1:8888/doc.html` 查看 `api` 文档\n8. *推荐使用IDEA打开项目文件夹自动处理依赖、方便运行*\n\n### [前台](https://github.com/WindSnowLi/vue-ssr-blog)\n\n1. `git clone https://github.com/WindSnowLi/vue-ssr-blog.git` 克隆项目到本地\n2. `npm install` 安装依赖\n3. 可修改 `config/sitemap.xml` 文件中的 `host` 地址，用于生成访问地图\n4. 可修改 `nuxt.config.js` 中的端口号\n5. 可修改 `package.json` 文件中的 `script` 中的 `BASE_URL` 来指定后端 `api` 地址\n6. `npm run build` 编译\n7. `npm start` 本地运行\n\n### [后台](https://github.com/WindSnowLi/vue-admin-blog)\n\n1. `git clone https://github.com/WindSnowLi/vue-admin-blog.git` 克隆项目到本地\n2. `npm install` 安装依赖\n3. `npm run dev` 使用模拟数据预览界面\n4. 修改 `.env.production` 文件中的 `VUE_APP_BASE_API` 地址为后端 `api` 的地址\n5. `npm run build:prod` 编译\n6. `dist` 文件夹下的为编译好的文件，可放到 `http` 服务器下（可以使用 `npm` 安装 `http-server` ）进行访问\n\n## 界面展示\n\n### 前台\n\n![首页](https://pic.hiyj.cn/images/2021/08/30/8be350dc4ad9f76c10f7daa8f0ec2f83.png)\n\n<br>\n\n![文章详情](https://pic.hiyj.cn/images/2021/08/30/d921a4e9d688c8e42e4cb491e81ea29f.png)\n\n<br>\n\n![友链](https://pic.hiyj.cn/images/2021/08/30/1e6a95dba9dffe2c518fb1114d27f9ef.png)\n\n<br>\n\n![关于信息](https://pic.hiyj.cn/images/2021/08/30/2a71cb94b94aed68d5628ee41beb0359.png)\n\n### 后台\n\n![首页](https://pic.hiyj.cn/images/2021/08/30/c058c6879cad0dc8db994a7dc57f1de6.png)\n\n<br>\n\n![创建文章](https://pic.hiyj.cn/images/2021/08/30/5a9b1e429a934801704cc9ef9526ff60.png)\n\n<br>\n\n![管理文章](https://pic.hiyj.cn/images/2021/08/30/b9a4cc395e4e02ff996c198f39c10895.png)\n\n![文章列表](https://pic.hiyj.cn/images/2021/08/30/7e69e00b415213d37034dd49d236d18e.png)\n\n<br>\n\n![友链管理](https://pic.hiyj.cn/images/2021/08/30/2cd2f03ab3eab5ea0e14a7ced2695d09.png)\n\n<br>\n\n![关于信息](https://pic.hiyj.cn/images/2021/08/30/5582f506f1a93b114baef9d9977841ea.png)\n'),(131,1,'topbar_title','纯天然色的菜狗'),(132,1,'main_title','WindSnowLi'),(133,1,'footer','');
/*!40000 ALTER TABLE `ui_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `account` varchar(100) NOT NULL COMMENT '账户',
                        `password` varchar(100) NOT NULL COMMENT '密码',
                        `avatar` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '头像',
                        `nickname` varchar(100) NOT NULL COMMENT '昵称',
                        `qq` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'QQ',
                        `introduction` varchar(100) DEFAULT NULL COMMENT '个人介绍',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `user_UN` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@163.com','b8a1099b57fb53d28fba7d5717e317ea','https://pic.hiyj.cn/images/2021/07/04/7c99ac62e6de300a5256ec3be56cb2a1.gif','WindSnowLi','706623475','倾向于学习C/C++,Python,Java，主要学习后端方向，虽说很菜但很可爱，虽然啥也不会');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_map_article`
--

DROP TABLE IF EXISTS `user_map_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_map_article` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `user_id` int NOT NULL COMMENT '用户ID',
                                    `article_id` int NOT NULL COMMENT '文章ID',
                                    PRIMARY KEY (`id`),
                                    KEY `user_map_article_FK` (`article_id`),
                                    KEY `user_map_article_FK_1` (`user_id`),
                                    CONSTRAINT `user_map_article_FK` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CONSTRAINT `user_map_article_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_map_article`
--

LOCK TABLES `user_map_article` WRITE;
/*!40000 ALTER TABLE `user_map_article` DISABLE KEYS */;
INSERT INTO `user_map_article` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_map_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_map_file`
--

DROP TABLE IF EXISTS `user_map_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_map_file` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `user_id` int NOT NULL COMMENT '用户id',
                                 `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属文件路径',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `user_map_file_UN` (`user_id`,`file_name`),
                                 CONSTRAINT `user_map_file_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_map_file`
--

LOCK TABLES `user_map_file` WRITE;
/*!40000 ALTER TABLE `user_map_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_map_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_map_role`
--

DROP TABLE IF EXISTS `user_map_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_map_role` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `user_id` int NOT NULL COMMENT '用户ID',
                                 `role_id` int NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `user_map_role_un` (`user_id`,`role_id`),
                                 KEY `user_map_role_FK_1` (`role_id`),
                                 CONSTRAINT `user_map_role_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT `user_map_role_FK_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_map_role`
--

LOCK TABLES `user_map_role` WRITE;
/*!40000 ALTER TABLE `user_map_role` DISABLE KEYS */;
INSERT INTO `user_map_role` VALUES (1,1,2);
/*!40000 ALTER TABLE `user_map_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visits_count`
--

DROP TABLE IF EXISTS `visits_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visits_count` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `target_id` int NOT NULL COMMENT '目标ID',
                                `type` int NOT NULL COMMENT '类型，1文章，10标签，100分类',
                                `count` int NOT NULL DEFAULT '0' COMMENT '总次数',
                                `time` datetime NOT NULL COMMENT '某天访问量',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `visits_count_UN` (`target_id`,`type`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visits_count`
--

LOCK TABLES `visits_count` WRITE;
/*!40000 ALTER TABLE `visits_count` DISABLE KEYS */;
/*!40000 ALTER TABLE `visits_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'blog'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-02 21:44:05